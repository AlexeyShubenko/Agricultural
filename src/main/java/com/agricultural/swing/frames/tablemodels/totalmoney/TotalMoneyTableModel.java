package com.agricultural.swing.frames.tablemodels.totalmoney;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import lombok.Data;

import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Alexey on 12.03.2017.
 */
@Data
public class TotalMoneyTableModel extends AbstractTableModel {

    private String [] columnName = {"","","","","Отриманого всього палива","","","Повна вартість робіт, грн","",""};

    private final Integer COLUMN_NUMBER = 10;
    private final Integer ROW_NUMBER = 1;

    private String[][] data = new String[ROW_NUMBER][COLUMN_NUMBER];
    private double totalMoney=0;
    private double totalAreaOrWorkHour=0;
    private double totalGivenFuel=0;
    private double totalUsedFuel=0;
    private double totalWorkCost=0;
    ///кінцева, сумарна сума грошей
    private double totalForAll=0;

    public TotalMoneyTableModel(List<DriverDataHectare> allDataHectare, List<DriverDataHour> allDataHour){
            if (allDataHectare != null) {
                for (int i = 0; i < allDataHectare.size(); i++) {
                    totalMoney += allDataHectare.get(i).getOverallWorkCost();
                    totalAreaOrWorkHour += allDataHectare.get(i).getCultivatedArea();
                    totalGivenFuel += allDataHectare.get(i).getGivenFuel();
                    totalUsedFuel += allDataHectare.get(i).getUsedFuel();

                    totalWorkCost += allDataHectare.get(i).getWorkCost();
                }
            }
            if (allDataHour != null) {
                for (int i = 0; i < allDataHour.size(); i++) {
                    totalMoney += allDataHour.get(i).getOverallWorkCost();
                    totalAreaOrWorkHour += allDataHour.get(i).getWorkedHours();
                    totalGivenFuel += allDataHour.get(i).getGivenFuel();
                    totalUsedFuel += allDataHour.get(i).getUsedFuel();
                    totalWorkCost += allDataHour.get(i).getWorkCost();
                }
            }

            double usedFuel = new BigDecimal(totalUsedFuel).setScale(2,BigDecimal.ROUND_UP).doubleValue();
            double workCost = new BigDecimal(totalWorkCost).setScale(2,BigDecimal.ROUND_UP).doubleValue();

            data[0][1] = "Разом";
            data[0][3] = String.valueOf(totalAreaOrWorkHour);
            data[0][4] = String.valueOf(totalGivenFuel);
            data[0][5] = String.valueOf(usedFuel);
//            data[0][5] = String.format(".2f",totalUsedFuel);
//            BigDecimal workCost = new BigDecimal(totalWorkCost);
//            workCost.setScale(4,BigDecimal.ROUND_HALF_UP);
            data[0][6] = String.valueOf(workCost);
            data[0][7] = String.valueOf(totalMoney);
    }

    public TotalMoneyTableModel(double totalMoneyHectare,double totalGivenFuelHectare,
                                double totalMoneyHour,double totalGivenFuelHour){
        data[0][1] = "Разом";
//        data[0][3] = "";
        data[0][4] = String.valueOf(totalGivenFuelHectare+totalGivenFuelHour);
//        data[0][6] = "";
        this.totalForAll = totalMoneyHectare+totalMoneyHour;
        data[0][7] = String.valueOf(totalForAll);

    }
    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public int getRowCount() {
        return ROW_NUMBER;
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NUMBER;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }
}
