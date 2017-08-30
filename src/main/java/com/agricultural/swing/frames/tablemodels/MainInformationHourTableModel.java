package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.hectareinformation.InformationHectareDAOImpl;
import com.agricultural.dao.tractordrivers.TractorDriverDAOImpl;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 23.02.2017.
 */
public class MainInformationHourTableModel extends AbstractTableModel {

    private TractorDriverDAOImpl service = new TractorDriverDAOImpl();

    private String[] columnNames = {"№", "Технологічна операція","Машино-тракторний агрегат","Відпрацьовано годин",
    "Отримано палива, л", "Витрата палива, л/год", "Вартість робіт, грн/год", "Загальна вартість робіт, грн","->","delete"};
    private String[][] data;

    private ArrayList<Integer> updatesNumber = new ArrayList();
    private final Integer COLUMN_NUMBER = 10;
    List<DriverDataHour> driverDataHours;

    private InformationHectareDAOImpl infService = new InformationHectareDAOImpl();

    public MainInformationHourTableModel(List<DriverDataHour> driverDataHours) {
        this.driverDataHours = driverDataHours;
        if(driverDataHours!=null) {
            ///створються масив з даними, який будуть виводитися у таблицю
            data = new String[this.driverDataHours.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
            for (int i = 0; i < driverDataHours.size(); i++) {
                data[i][0] = ++i + ""; --i;
                data[i][1] = driverDataHours.get(i).getOperation().getName();
                data[i][2] = driverDataHours.get(i).getMachine().getName();
                data[i][3] = String.valueOf(driverDataHours.get(i).getWorkedHours());
                data[i][4] = String.valueOf(driverDataHours.get(i).getGivenFuel());
                data[i][5] = String.valueOf(driverDataHours.get(i).getUsedFuel());
                data[i][6] = String.valueOf(driverDataHours.get(i).getWorkCost());
                data[i][7] = String.valueOf(driverDataHours.get(i).getOverallWorkCost());
                data[i][8] = "->";
                data[i][9] = "-";
            }
        }else {
            this.driverDataHours = new ArrayList<>();
            data = new String[this.driverDataHours.size()][COLUMN_NUMBER];
        }
    }

    public void updateTableData() {

        for (int i=0; i<updatesNumber.size();i++) {
            ///row - номер рядка таблиці в якому були змінені данні
            int row = updatesNumber.get(i);
            ///оновлення  данних, та відправлення запроу в базу
            ///в таблиці може змінюватися тільки 6 колонка
            if (data[row][6].contains(",")) {
                data[row][6] = data[row][6].replace(",", ".");
            }
            driverDataHours.get(row).setWorkCost(Double.valueOf(data[row][6]));
            driverDataHours.get(row).calcOverallWorkCost();///перерахунок ціни повної роботи
            infService.editDriverDataHour(driverDataHours.get(row));
        }
    }


    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
         /*так як дані можу бути зміненні в одному рядку в кількох колонках
          то здійснюється перевірка на наявність зміненого рядка в списку*/
        if(!updatesNumber.contains(rowIndex)) updatesNumber.add(rowIndex);
        ///змінені дані обновляються в масиві data
        data[rowIndex][columnIndex] = ((String) aValue).trim();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public int getRowCount() {
        return data.length;
    }

    public int getColumnCount() {
        return COLUMN_NUMBER;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex==6)return true;
        return false;
    }
}
