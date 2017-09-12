package com.agricultural.swing.frames.tablemodels;

import com.agricultural.dao.workplaces.WorkplaceDaoImpl;
import com.agricultural.domains.main.Workplace;
import com.agricultural.service.WorkplaceService;
import com.agricultural.service.impl.WorkplaceServiceImpl;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * Created by Alexey on 23.02.2017.
 */
public class WorkplaceTableModel extends AbstractTableModel {

    private WorkplaceService workplaceService = WorkplaceServiceImpl.getInstance();

    private ArrayList<Workplace> workplaces;
    private String[] columnNames = {"№", "За місцем роботи","Видалити"};
    private String[][] data;
    private ArrayList<Integer> updatesNumber = new ArrayList();

    private final Integer COLUMN_NUMBER = 3;
    private final Integer UNCHANGEABLE_COLUMN_NUMBER = 0;

    public WorkplaceTableModel(ArrayList<Workplace> workplaces) {
        this.workplaces = workplaces;
        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[workplaces.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < workplaces.size(); i++) {
            ///порядковий номер рядка у таблиці (не пов'язаний з даними)
            data[i][0] = ++i + ""; --i;
            ///назва технологічної операції, яка записується в 2 колонку таблиці
            data[i][1] = workplaces.get(i).getWorkPlaceName();
            data[i][2] = "-";
        }
    }

    public void updateTableData() {

        for (int i=0; i<updatesNumber.size();i++) {
            ///row - номер рядка таблиці в якому були змінені данні
            int row = updatesNumber.get(i);
            ///перевірка на наявність
//            boolean flag = false;
//            for (int j = 0; j < workplaces.size(); j++) {
//                if (workplaces.get(i).getWorkPlaceName().compareToIgnoreCase(data[row][1]) == 0) {
//                    //якщо таке значення існує то не записувати у базу
//                    flag = true;
//                    break;
//                }
//            }
//            if (flag) {
//                ///не записувати
//                JOptionPane.showMessageDialog(null, "Значення \"" + data[row][1] + "\"  вже існує",
//                        "Увага!", JOptionPane.ERROR_MESSAGE);
//            } else {
                ///оновлення  данних, та відправлення запросу в базу
                workplaces.get(row).setWorkPlaceName(data[row][1]);
                workplaceService.editWorkplace(workplaces.get(row));

//            }
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
        if(columnIndex==UNCHANGEABLE_COLUMN_NUMBER)return false;
        else return true;
    }
}
