package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.tractordrivers.TractorDriverDaoImpl;
import com.agricultural.domains.main.TractorDriver;
import com.agricultural.service.TractorDriverService;
import com.agricultural.service.impl.TractorDriverServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 23.02.2017.
 */
public class TractorDriverTableModel extends AbstractTableModel {

    private TractorDriverService tractorDriverService = TractorDriverServiceImpl.getInstance();

    private ArrayList<TractorDriver> drivers;
    private String[] columnNames = {"№", "Працівник","Тарифна ставка","Посада","За місцем роботи","Редагувати","Детальна інформація","Видалити"};
    private String[][] data;

    private final Integer COLUMN_NUMBER = 8;
    private final Integer UNCHANGEABLE_COLUMN_NUMBER = 0;

    public TractorDriverTableModel(ArrayList<TractorDriver> drivers) {
        this.drivers = drivers;
        try{
        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[drivers.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < drivers.size(); i++) {
            data[i][0] = ++i + "";
            --i;
            data[i][1] = drivers.get(i).getName();
            data[i][2] = String.valueOf(drivers.get(i).getWageRate());
            data[i][3] = drivers.get(i).getPosition();
            data[i][4] = drivers.get(i).getWorkplace().getWorkPlaceName();
            data[i][5] = "->"; ////редактування
            data[i][6] = "->";
            data[i][7] = "-";
        }
        }catch (NullPointerException ex){
            ex.printStackTrace();
            System.out.println("Елементів немає");
        }
    }

//    public void updateTableData() {
//
////        System.out.println(updatesNumber.toString());
////
////        for (int i=0; i<updatesNumber.size();i++) {
////            ///row - номер рядка таблиці в якому були змінені данні
////            int row = updatesNumber.get(i);
////            ///оновлення  данних, та відправлення запроу в базу
////            drivers.get(row).setName(data[row][1]);
////            drivers.get(row).setWageRate(Integer.valueOf(data[row][2]));
////            drivers.get(row).setPosition(data[row][3]);
////            drivers.get(row).getWorkplace(data[row][4]);
////            tractorDriverService.editTractorDriver(drivers.get(row));
////        }
//    }


//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        /*так як дані можу бути зміненні в одному рядку в кількох колонках
//          то здійснюється перевірка на наявність зміненого рядка в списку*/
//        if(!updatesNumber.contains(rowIndex)) updatesNumber.add(rowIndex);
//        ///змінені дані обновляються в масиві data
//        data[rowIndex][columnIndex] = ((String) aValue).trim();
//    }

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
//        if(columnIndex==UNCHANGEABLE_COLUMN_NUMBER || columnIndex==5)return false;
        return false;
    }
}
