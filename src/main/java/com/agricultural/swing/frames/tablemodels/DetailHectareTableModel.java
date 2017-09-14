package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.detailnformation.DetailInformationDAOImpl;
import com.agricultural.domains.DataMassive;
import com.agricultural.domains.gectarniyvirobitok.DetailDataHectare;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.service.InformationHectareService;
import com.agricultural.service.impl.InformationHectareServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 08.03.2017.
 */
public class DetailHectareTableModel extends AbstractTableModel {

    private String[] columnName = {"День","Виробіток (га/день)","Разом по наростаючі виробіток(га)","Отримано палива (л)","Разом по наростаючій отримано палива (л)","Витрата палива (л/га)"};
    private final Integer COLUMN_NUMBER = 6;
    private final Integer ROW_NUMBER = 31;

    private double[] cultivatedArea;
    private double[] cultivatedAreaIncrease;
    private double[] givenFuel;
    private double[] givenFuelIncrease;
    private double[] usedFuel;

    private String[][] data = new String[ROW_NUMBER][COLUMN_NUMBER];
    private DataMassive dataMassive;
    private ArrayList<Integer> updatesNumber = new ArrayList();
    private DetailDataHectare detailDataHectare;

    private InformationHectareService infoHectareService = InformationHectareServiceImpl.getInstance();
    private DetailInformationDAOImpl detailService = new DetailInformationDAOImpl();

    public DetailHectareTableModel(DetailDataHectare detailDataHectare){
        this.detailDataHectare = detailDataHectare;
        ///отримуємо дані у вигляді масива
        ///переводимо String -> double[]
        dataMassive = new DataMassive(detailDataHectare.getCultivatedAreaString(),
                detailDataHectare.getGivenFuelString(),detailDataHectare.getUsedFuelAreaString());
        ///звичайні дані
        cultivatedArea = dataMassive.getCultAreaOrWorkedHour();
        givenFuel = dataMassive.getGivenFuel();
        ///колонки даних по наростаючій
        cultivatedAreaIncrease = dataMassive.calculateIncreasingValues(cultivatedArea);
        givenFuelIncrease = dataMassive.calculateIncreasingValues(givenFuel);

        usedFuel = dataMassive.calculateUsedFuelMassive(givenFuelIncrease,cultivatedAreaIncrease);

        for(int i = 0; i< ROW_NUMBER; i++){
            data[i][0] = ++i+"";--i;

            if (cultivatedArea[i]!=0) data[i][1] = String.valueOf(cultivatedArea[i]);
                else data[i][1]="";
            if (cultivatedAreaIncrease[i]!=0)data[i][2] = String.valueOf(cultivatedAreaIncrease[i]);
                else data[i][2]="";
            if (givenFuel[i]!=0) data[i][3] = String.valueOf(givenFuel[i]);
            else data[i][3]="";

            if (givenFuelIncrease[i]!=0) data[i][4] = String.valueOf(givenFuelIncrease[i]);
            else data[i][4]="";

            if (givenFuelIncrease[i]!=0) data[i][5] = String.valueOf(usedFuel[i]);
            else data[i][5]="";
        }
    }

    public void updateTableData() {

        for (int i=0; i<updatesNumber.size();i++) {
            ///row - номер рядка таблиці в якому були змінені данні
            int row = updatesNumber.get(i);
            ///перевірка на пустоту
            if(data[row][3].trim().equals(""))data[row][3]="0.0";
            if(data[row][1].trim().equals(""))data[row][1]="0.0";
            ///перевірка на введення коми, значення колонок 1 і 3 можуть змінюватися
            if (data[row][1].contains(",")) {
                data[row][1] = data[row][1].replace(",", ".");
            }
            if (data[row][3].contains(",")) {
                data[row][3] = data[row][3].replace(",", ".");
            }
            ///оновлення  данних, та відправлення запросу в базу
            dataMassive.getCultAreaOrWorkedHour()[row]=Double.valueOf(data[row][1]);
            dataMassive.getGivenFuel()[row]=Double.valueOf(data[row][3]);
        }
        ///переводимо 3 масива double[] -> String i і записуємо у detailDataHectare
        detailDataHectare.setCultivatedAreaString(dataMassive.getStringFromDoubleMassive(dataMassive.getCultAreaOrWorkedHour()));
        detailDataHectare.setGivenFuelString(dataMassive.getStringFromDoubleMassive(dataMassive.getGivenFuel()));
        detailDataHectare.setUsedFuelAreaString(dataMassive.getStringFromDoubleMassive(dataMassive.calculateUsedFuelMassive()));
        ///записуємо в базу оновлені дані
        detailService.editDetailDataHectare(detailDataHectare);

        ///заповнюємо наступні дані для driverDataHectare
        ///"Оброблена площа, га","Отримано палива, л", "Витрата палива, л/га", "Вартість робіт, грн/га", "Загальна вартість робіт, грн"
        double totalCultivatedArea = dataMassive.getTotalResult(dataMassive.getCultAreaOrWorkedHour());
        double totalGivenFuel = dataMassive.getTotalResult(dataMassive.getGivenFuel());

        DriverDataHectare driverDataHectare = infoHectareService.getDriverDataHectareById(detailDataHectare.getDriverDataHectare().getData_id());
        driverDataHectare.setCultivatedArea(totalCultivatedArea);
        driverDataHectare.setGivenFuel(totalGivenFuel);
        double totalUsedFuel = dataMassive.calculateUsedFuel(totalGivenFuel,totalCultivatedArea);
        driverDataHectare.setUsedFuel(totalUsedFuel);
        driverDataHectare.calcOverallWorkCost(); ///обраховується загальна ціна роботи
        ///для обрахунку загальної суми грошей
        driverDataHectare.setOverallWorkCost(driverDataHectare.getCultivatedArea()*driverDataHectare.getWorkCost());

        infoHectareService.editDriverDataHectare(driverDataHectare);

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ///в список добавляються порядкові номера рядка дані якого були змінені
        if(!updatesNumber.contains(rowIndex)) updatesNumber.add(rowIndex);
        ///змінені дані обновляються в
        data[rowIndex][columnIndex] = ((String) aValue).trim();
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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return  (columnIndex==1)||(columnIndex==3)?true:false;
    }

}
