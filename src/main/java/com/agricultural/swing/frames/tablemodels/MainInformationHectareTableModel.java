package com.agricultural.swing.frames.tablemodels;

import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.service.InformationHectareService;
import com.agricultural.service.impl.InformationHectareServiceImpl;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 23.02.2017.
 */
public class MainInformationHectareTableModel extends AbstractTableModel {

    ImageIcon deleteIcon = new ImageIcon("delete.jpg");

    private String[] columnNames = {"№", "Технологічна операція","Машино-тракторний агрегат","Оброблена площа, га",
    "Отримано палива, л", "Витрата палива, л/га", "Вартість робіт, грн/га", "Загальна вартість робіт, грн","->","delete"};

    private String[][] data;

    private ArrayList<Integer> updatesNumber = new ArrayList();
    private final Integer COLUMN_NUMBER = 10;

    private List<DriverDataHectare> driverDataHectare;
    private InformationHectareService infoHectareService = InformationHectareServiceImpl.getInstance();

    public MainInformationHectareTableModel(List<DriverDataHectare> driverDataHectare) {
        this.driverDataHectare = driverDataHectare;
        if (driverDataHectare!=null){
        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[this.driverDataHectare.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < driverDataHectare.size(); i++) {
            data[i][0] = ++i + ""; --i;
            data[i][1] = driverDataHectare.get(i).getOperation().getName();
            data[i][2] = driverDataHectare.get(i).getMachine().getName();
            data[i][3] = String.valueOf(driverDataHectare.get(i).getCultivatedArea());
            data[i][4] = String.valueOf(driverDataHectare.get(i).getGivenFuel());
            data[i][5] = String.valueOf(driverDataHectare.get(i).getUsedFuel());
            data[i][6] = String.valueOf(driverDataHectare.get(i).getWorkCost());
            data[i][7] = String.valueOf(driverDataHectare.get(i).getOverallWorkCost());
            data[i][8] = "->";
            data[i][9] = "-";
            }
        }else {
            this.driverDataHectare = new ArrayList<>();
            data = new String[this.driverDataHectare.size()][COLUMN_NUMBER];
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
            BigDecimal workCost = new BigDecimal(Double.valueOf(data[row][6]));
            workCost = workCost.multiply(new BigDecimal(1), new MathContext(4, RoundingMode.HALF_UP));
            driverDataHectare.get(row).setWorkCost(workCost.doubleValue());

            driverDataHectare.get(row).calcOverallWorkCost();///перерахунок ціни повної роботи
            infoHectareService.editDriverDataHectare(driverDataHectare.get(row));
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
