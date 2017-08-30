package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.operations.OperationDAOImpl;
import com.agricultural.domains.main.TechnologicalOperation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 23.02.2017.
 */
public class OperationTableModel extends AbstractTableModel {

    private OperationDAOImpl service = new OperationDAOImpl();

    private ArrayList<TechnologicalOperation> operations;
    private String[] columnNames = {"№", "Технологічна операція","Видалити"};
    private String[][] data;
    private ArrayList<Integer> updatesNumber = new ArrayList();

    private final Integer COLUMN_NUMBER = 3;
    private final Integer UNCHANGEABLE_COLUMN_NUMBER = 0;

    public OperationTableModel(ArrayList<TechnologicalOperation> operations) {
        this.operations = operations;
        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[operations.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < operations.size(); i++) {
            ///порядковий номер рядка у таблиці (не пов'язаний з даними)
            data[i][0] = ++i + ""; --i;
            ///назва технологічної операції, яка записується в 2 колонку таблиці
            data[i][1] = operations.get(i).getName();
            data[i][2] = "-";

        }
    }

    public void updateTableData() {

        for (int i=0; i<updatesNumber.size();i++) {
            ///row - номер рядка таблиці в якому були змінені данні
            int row = updatesNumber.get(i);
            ///оновлення  данних, та відправлення запросу в базу
            operations.get(row).setName(data[row][1]);
            service.editOperation(operations.get(row));
        }
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
        if((columnIndex==UNCHANGEABLE_COLUMN_NUMBER)&&(columnIndex==3))return false;
        else return true;
    }
}
