package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.detailnformation.DetailInformationDaoImpl;
import com.agricultural.domains.DataMassive;
import com.agricultural.domains.hoursvirobitok.DetailDataHour;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.service.DetailInformationService;
import com.agricultural.service.InformationHourService;
import com.agricultural.service.impl.DetailInformationServiceImpl;
import com.agricultural.service.impl.InformationHourServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 08.03.2017.
 */
public class DetailHourTableModel extends AbstractTableModel {

    private String[] columnName = {"День","Відпрацьовано годи (год)","Разом по наростаючій годин (год)","Отримано палива (л)","Разом по наростаючій отримано палива (л)","Витрата палива (л/год)"};
    private final Integer COLUMN_NUMBER = 6;
    private final Integer ROW_NUMBER = 31;

    private double[] workedHour;
    private double[] workedHourIncrease;
    private double[] givenFuel;
    private double[] givenFuelIncrease;
    private double[] usedFuel;

    private String[][] data = new String[ROW_NUMBER][COLUMN_NUMBER];
    private DataMassive dataMassive;
    private ArrayList<Integer> updatesNumber = new ArrayList();
    private DetailDataHour detailDataHour;

    private InformationHourService infoHourService = InformationHourServiceImpl.getInstance();
    private DetailInformationService detailService = DetailInformationServiceImpl.getInstance();

    public DetailHourTableModel(DetailDataHour detailDataHour){
        this.detailDataHour = detailDataHour;
        ///отримуємо дані у вигляді масива
        dataMassive = new DataMassive(detailDataHour.getWorkedHoursString(),
                detailDataHour.getGivenFuelString(),detailDataHour.getUsedFuelAreaString());
        workedHour = dataMassive.getCultAreaOrWorkedHour();
        givenFuel = dataMassive.getGivenFuel();

        workedHourIncrease = dataMassive.calculateIncreasingValues(workedHour);
        givenFuelIncrease = dataMassive.calculateIncreasingValues(givenFuel);

        usedFuel = dataMassive.calculateUsedFuelMassive(givenFuelIncrease,workedHourIncrease);

        for(int i = 0; i< ROW_NUMBER; i++){
            data[i][0] = ++i+"";--i;

            if (workedHour[i]!=0)data[i][1] = String.valueOf(workedHour[i]);
                else data[i][1]="";

            if (workedHourIncrease[i]!=0)
            data[i][2] = String.valueOf(workedHourIncrease[i]);
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
            ///перевірка на введення коми
            if (data[row][3].contains(",")) {
                data[row][3] = data[row][3].replace(",", ".");
            }
            if (data[row][1].contains(",")) {
                data[row][1] = data[row][1].replace(",", ".");
            }
            ///оновлення  данних, та відправлення запроу в базу
            dataMassive.getCultAreaOrWorkedHour()[row]=Double.valueOf(data[row][1]);
            dataMassive.getGivenFuel()[row]=Double.valueOf(data[row][3]);
        }
        ///переводимо 3 масива double[] -> String i і записуємо у detailDataHectare
        detailDataHour.setWorkedHoursString(dataMassive.getStringFromDoubleMassive(dataMassive.getCultAreaOrWorkedHour()));
        detailDataHour.setGivenFuelString(dataMassive.getStringFromDoubleMassive(dataMassive.getGivenFuel()));
        detailDataHour.setUsedFuelAreaString(dataMassive.getStringFromDoubleMassive(dataMassive.calculateUsedFuelMassive()));
        detailService.editDetailDataHour(detailDataHour);

        double totalWorkedHour = dataMassive.getTotalResult(dataMassive.getCultAreaOrWorkedHour());
        double totalGivenFuel = dataMassive.getTotalResult(dataMassive.getGivenFuel());
//        double totalUsedFuel = dataMassive.getTotalResult(dataMassive.getUsedFuel());

        ///заповнюємо наступні дані для driverDataHectare
        ///"Відпрацьовано годин, год","Отримано палива, л", "Витрата палива, л/га", "Вартість робіт, грн/га", "Загальна вартість робіт, грн"
        DriverDataHour driverDataHour = infoHourService.getDriverDataHourById(detailDataHour.getDriverDataHour().getData_id());
        driverDataHour.setWorkedHours(totalWorkedHour);
        driverDataHour.setGivenFuel(totalGivenFuel);
        double totalUsedFuel = dataMassive.calculateUsedFuel(driverDataHour.getGivenFuel(),driverDataHour.getWorkedHours());

        driverDataHour.setUsedFuel(totalUsedFuel);
        driverDataHour.calcOverallWorkCost(); ///обраховується загальна ціна роботи
        ///для обрахунку загальної суми грошей
        driverDataHour.setOverallWorkCost(driverDataHour.getWorkedHours()*driverDataHour.getWorkCost());

        infoHourService.editDriverDataHour(driverDataHour);

    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ///в список добавляються порядкові номера рядка дані якого були змінені
        updatesNumber.add(rowIndex);
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
