package com.agricultural.swing.frames.tablemodels;

import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.service.MachineService;
import com.agricultural.service.impl.MachineServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 23.02.2017.
 */
public class MachineTableModel extends AbstractTableModel {

    private MachineService machineService = MachineServiceImpl.getInstance();

    private ArrayList<MachineTractorUnit> machines;
    private String[] columnNames = {"№", "Машинно тракторний агрегат","Видалити"};
    private String[][] data;

    private ArrayList<Integer> updatesNumber = new ArrayList();
    private final Integer COLUMN_NUMBER = 3;
    private final Integer UNCHANGEABLE_COLUMN_NUMBER = 0;

    public MachineTableModel(ArrayList<MachineTractorUnit> machines) {
        this.machines = machines;
        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[machines.size()][COLUMN_NUMBER ];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < machines.size(); i++) {
            ///порядковий номер рядка у таблиці (не пов'язаний з даними)
            data[i][0] = ++i + ""; --i;
            ///назва машинно-тракторного агрегату, яка записується в 2 колонку таблиці
            data[i][1] = machines.get(i).getName();
            data[i][2] = "-";
        }
    }

    public void updateTableData() {

        for (int i=0; i<updatesNumber.size();i++) {
            ///row - номер рядка таблиці в якому були змінені данні
            int row = updatesNumber.get(i);
            ///оновлення  данних, та відправлення запроу в базу
            machines.get(row).setName(data[row][1]);
            machineService.editMachine(machines.get(row));
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ///в список добавляються порядкові номера рядка дані якого були змінені
        updatesNumber.add(rowIndex);
        ///змінені дані обновляються в data
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
        return COLUMN_NUMBER ;
    }


    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if((columnIndex==UNCHANGEABLE_COLUMN_NUMBER)&&columnIndex==3)return false;
        else return true;
    }
}