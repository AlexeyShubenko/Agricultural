package com.agricultural.domains;

import lombok.Data;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.DoubleStream;

/**
 * Created by Alexey on 11.03.2017.
 */
@Data
public class DataMassive {

    private static final Integer MASSIVE_LENGTH=31;
    ///оброблена площа, виробіток. 31 день
    private double[] cultAreaOrWorkedHour = new double[MASSIVE_LENGTH];
    ///оброблена плоца РАЗОМ по наротаючій
    private double[] areaOrHourIncreasing = new double[MASSIVE_LENGTH];

    ///отримане палива
    private double[] givenFuel = new double[MASSIVE_LENGTH];
    ///отримане паливо Разом по наростаючій
    private double[] givenFuelIncreasing = new double[MASSIVE_LENGTH];

    ///використано палива
    private double[] usedFuel = new double[MASSIVE_LENGTH];


    public DataMassive(String cultivatedAreaString, String givenFuelString, String usedFuelString){
        if(cultivatedAreaString!=null)
            this.cultAreaOrWorkedHour = getDoubleMassiveFromString(cultivatedAreaString);
        if(givenFuelString!=null)
            this.givenFuel = getDoubleMassiveFromString(givenFuelString);
        if(usedFuelString!=null)
            this.usedFuel = getDoubleMassiveFromString(usedFuelString);
    }

    ///метод визивається після після заповнення основних масивів
    public double[] calculateIncreasingValues(double[] forIncrease){
        double[] increaseValue = new double[MASSIVE_LENGTH];
        increaseValue[0] = forIncrease[0];
        for(int i = 1; i<MASSIVE_LENGTH; i++){
            increaseValue[i] = increaseValue[i-1] + forIncrease[i];
        }
        return increaseValue;
    }

    ///повертає масив у вигляді рядка, який буде записаний у базу
    public static String getStringFromDoubleMassive(double[] massive){
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i<massive.length;i++){
            builder.append(massive[i]).append(" ");
        }
        return builder.toString().trim();
    }

    public static double[] getDoubleMassiveFromString(String toMas){
        double[] massive;
        massive = Arrays.stream(toMas.split(" ")).mapToDouble(Double::parseDouble).toArray();
        return massive;
    }

    ///обрахвати використане пальне
    public double calculateUsedFuel(double givenFuel, double cultAreaOrWorkedHour){
        double usedFuel;
        try {
                BigDecimal gFuel = new BigDecimal(givenFuel);
                BigDecimal cArea = new BigDecimal(cultAreaOrWorkedHour);
                usedFuel = gFuel.divide(cArea, 2, BigDecimal.ROUND_UP).doubleValue();
            } catch (ArithmeticException e) {
                ///якщо одне з 2-х полів не заповнене то записується 0
                usedFuel=0;
            }
        return usedFuel;
    }

    ///обрахувати використане пальне
    public double[] calculateUsedFuelMassive(/*double[] givenFuel, double[] cultivatedArea*/){
        for(int i = 0; i<31; i++) {
            try {
                BigDecimal gFuel = new BigDecimal(givenFuel[i]);
                BigDecimal cArea = new BigDecimal(cultAreaOrWorkedHour[i]);
                usedFuel[i] = gFuel.divide(cArea, 2, BigDecimal.ROUND_UP).doubleValue();
            } catch (ArithmeticException e) {
                ///якщо одне з 2-х полів не заповнене то записується 0
                usedFuel[i]=0;
            }
        }
        return usedFuel;
    }

    ///обрахувати використане пальне для Разом по наростаючій
    public double[] calculateUsedFuelMassive(double[] givenFuelIncrease, double[] cultivatedAreaIncrease){
        double[] usedFuel = new double[MASSIVE_LENGTH];
        for(int i = 0; i<31; i++) {
            try {
                BigDecimal gFuel = new BigDecimal(givenFuelIncrease[i]);
                BigDecimal cArea = new BigDecimal(cultivatedAreaIncrease[i]);

                if(((givenFuel[i]==0) || (cultAreaOrWorkedHour[i]==0))&& i==0)usedFuel[i]=0;
                else
                    if((givenFuel[i]==0) || (cultAreaOrWorkedHour[i]==0))usedFuel[i] = usedFuel[i-1];
                else
                    usedFuel[i] = gFuel.divide(cArea, 2, BigDecimal.ROUND_UP).doubleValue();

            } catch (ArithmeticException e) {
                ///якщо одне з 2-х полів не заповнене то записується 0
                usedFuel[i]=0;
            }
        }
        return usedFuel;
    }

    public double getTotalResult(double[] massive){
        BigDecimal total= new BigDecimal(0);
        ///сума всіх елементів
        for (int i = 0; i <massive.length ; i++) {
            BigDecimal bigDecimal = new BigDecimal(massive[i]);
            total = total.add(bigDecimal,new MathContext(2, RoundingMode.UP));
        }
//        total = DoubleStream.of(massive).sum();
        return total.doubleValue();
    }



}
