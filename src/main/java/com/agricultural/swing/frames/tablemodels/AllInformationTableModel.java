package com.agricultural.swing.frames.tablemodels;

import com.agricultural.domains.DataMassive;
import com.agricultural.domains.gectarniyvirobitok.DriverDataHectare;
import com.agricultural.domains.hoursvirobitok.DriverDataHour;
import com.agricultural.swing.frames.allinformation.DataInformation;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexey on 23.02.2017.
 */
public class AllInformationTableModel extends AbstractTableModel {

    private String[] columnNames = {"№","Операція","Машинно тракторний агрегат","1","2","3","4","5","6","7","8","9","10"
            ,"11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};

    private String[][] data;

    private final Integer COLUMN_NUMBER = 34;

    private ArrayList<DataInformation> allDataInformations = new ArrayList<>();
    private ArrayList<DataInformation> dataInformations = new ArrayList<>();

    public AllInformationTableModel(List<DriverDataHectare> employeesHectare, List<DriverDataHour> employeesHour) {
        ///Для гектарного виробітку

        if(employeesHectare!=null && employeesHour==null){
            fillHectareInformation(employeesHectare);
        }else
            if(employeesHour!=null && employeesHectare==null) {
                fillHourInformation(employeesHour);
            }else{
                fillUsedFuelInformation(employeesHectare,employeesHour);
        }

        ///створються масив з даними, який будуть виводитися у таблицю
        data = new String[dataInformations.size()][COLUMN_NUMBER];
        ///заповнення масива даними який буде виводитися у таблицю
        for (int i = 0; i < dataInformations.size(); i++) {
            data[i][0] = ++i + ""; --i;
            data[i][1] = dataInformations.get(i).getOperation();
            data[i][2] = dataInformations.get(i).getMachine();

            double[] dataMonth = dataInformations.get(i).getMonthData();
            for (int j = 0; j < 31; j++) {
                if(dataMonth[j]!=0)
                    data[i][j+3] = String.valueOf(dataMonth[j]);
            }
        }


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
        return false;
    }

    ///заповнення інформації по гектарному виробітку
    private void fillHectareInformation(List<DriverDataHectare> employeesHectare){
        ///перевірка на наявність однакових пар операці-машина
        for (int i = 0; i < employeesHectare.size(); i++) {
            String operationName = employeesHectare.get(i).getOperation().getName();
            String machineName = employeesHectare.get(i).getMachine().getName();
            double[] monthData = DataMassive.getDoubleMassiveFromString(employeesHectare.get(i).getDetailDataHectare().getCultivatedAreaString());
//            String strData = employeesHectare.get(i).getDetailDataHectare().getCultivatedAreaString();
            ///якщо перший елемент то додаємо його зразу в масив
            DataInformation dataInformation = new DataInformation(operationName, machineName, monthData);
            allDataInformations.add(dataInformation);
        }

        calculateAllMonthData();
    }

    ///заповнення інформації по годинному виробітку
    private void fillHourInformation(List<DriverDataHour> employeesHour){
        ///перевірка на наявність однакових пар операці-машина
        for (int i = 0; i < employeesHour.size(); i++) {
            String operationName = employeesHour.get(i).getOperation().getName();
            String machineName = employeesHour.get(i).getMachine().getName();
            double[] monthData = DataMassive.getDoubleMassiveFromString(employeesHour.get(i).getDetailDataHour().getWorkedHoursString());
            ///якщо перший елемент то додаємо його зразу в масив
            DataInformation dataInformation = new DataInformation(operationName, machineName, monthData);
            allDataInformations.add(dataInformation);
        }
        calculateAllMonthData();

    }

    ///заповнення інформації по використаному паливу
    private void fillUsedFuelInformation(List<DriverDataHectare> employeesHectare, List<DriverDataHour> employeesHour) {
        int totalSize = employeesHectare.size()+employeesHour.size();
        ///перевірка на наявність однакових пар операці-машина
        for (int i = 0; i < employeesHectare.size(); i++) {
            String operationName = employeesHectare.get(i).getOperation().getName();
            String machineName = employeesHectare.get(i).getMachine().getName();
            double[] monthData = DataMassive.getDoubleMassiveFromString(employeesHectare.get(i).getDetailDataHectare().getGivenFuelString());
            ///якщо перший елемент то додаємо його зразу в масив
            DataInformation dataInformation = new DataInformation(operationName, machineName, monthData);
            allDataInformations.add(dataInformation);
        }
        for (int i = 0; i < employeesHour.size(); i++) {
            String operationName = employeesHour.get(i).getOperation().getName();
            String machineName = employeesHour.get(i).getMachine().getName();
            double[] monthData = DataMassive.getDoubleMassiveFromString(employeesHour.get(i).getDetailDataHour().getGivenFuelString());
            ///якщо перший елемент то додаємо його зразу в масив
            DataInformation dataInformation = new DataInformation(operationName, machineName, monthData);
            allDataInformations.add(dataInformation);
        }
        calculateAllMonthData();
    }

    ///для сумування даних по місяцю
    private void calculateAllMonthData(){
        ///для попереднього запису першого рядка даних
        if (allDataInformations.size() > 0) {
            dataInformations.add(allDataInformations.get(0));
            allDataInformations.remove(0);
        }

        ///додавання даних по однаковим операціям і машинам
        for (int i = 0; i < allDataInformations.size(); i++) {
            DataInformation infoAll = allDataInformations.get(i);

            ///перевірка на наявність однакових записів
            boolean flag = false;
            DataInformation sameData = null;
            for (DataInformation data : dataInformations) {
                if (infoAll.getMachine().equals(data.getMachine())
                        && infoAll.getOperation().equals(data.getOperation())) {
                    flag = true;
                    sameData = data;
                    break;
                }
            }
            if (flag) {
                ///сумуємо дані за місяць
                double[] currentMassiveData = sameData.getMonthData();
                for (int k = 0; k < 31; k++) {
                    currentMassiveData[k] += infoAll.getMonthData()[k];
                }
            } else {
                dataInformations.add(infoAll);
            }
        }
    }


}