package com.agricultural.domains;

import com.agricultural.domains.main.TractorDriver;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Alexey on 12.03.2017.
 */
public class ExcelDataWriter {


    public static void writeToExcelOperationMachine(TableModel tableModel, String sheetName,boolean isOperation) throws IOException {
        File file;
        if (isOperation) {
            file = new File("operations.xls");
        }else{
            file = new File("machines.xls");
        }
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);
        Row rowHeader = sheet.createRow(0);
        for(int i=0; i<tableModel.getColumnCount();i++) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellValue(tableModel.getColumnName(i));
        }

        for(int i=0; i<tableModel.getRowCount();i++) {
            ///і+1 бо в нульовий рядок записано table column name
            Row row = sheet.createRow(i+1);
            for(int j=0; j<tableModel.getColumnCount()-1;j++){
                    Cell cell = row.createCell(j);
                    cell.setCellValue((String) tableModel.getValueAt(i,j));
            }
        }
        sheet.autoSizeColumn(1);
        workbook.write(new FileOutputStream(file));
        workbook.close();
    }



    public static void writeToExcelAllDriverInformation(TractorDriver driver, String month, String year,
                                                        TableModel tableModelHectare, TableModel tableModelTotalHectare, TableModel tableModelHour, TableModel tableModelTotalHour, TableModel tableModelTotal) throws IOException {
        final int SHIFT_FROM_LEFT = 2;
        ///колонки, які не треба зберігати
        final int TRANSIENT_COLUMN = 2;

        File file = new File(driver.getName()+".xls");
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("ТОВ \"МГМ АГРО \" " + driver.getName());
        Row rowhead0 = sheet.createRow(0);
        Cell cell01 = rowhead0.createCell(0);
        cell01.setCellValue("Посада");
        Cell cell02 = rowhead0.createCell(1);
        cell02.setCellValue(driver.getPosition());
        Cell cell03 = rowhead0.createCell(3);
        cell03.setCellValue("Місяць");

        Cell cell04 = rowhead0.createCell(4);
        cell04.setCellValue(month);
        Cell cell05 = rowhead0.createCell(6);
        cell05.setCellValue("Рік");
        Cell cell06 = rowhead0.createCell(7);
        cell06.setCellValue(year);

        Row rowhead1 = sheet.createRow(1);
        Cell cell10 = rowhead1.createCell(0);
        cell10.setCellValue("ПІБ");
        Cell cell11 = rowhead1.createCell(1);
        cell11.setCellValue(driver.getName());

        Row rowhead2 = sheet.createRow(2);
        Cell cell20 = rowhead2.createCell(0);
        cell20.setCellValue("За місцем роботи");
        Cell cell21 = rowhead2.createCell(1);
        cell21.setCellValue(driver.getWorkplace().getWorkPlaceName());

        Row rowhead3 = sheet.createRow(3);
        Cell cell30 = rowhead3.createCell(0);
        cell30.setCellValue("Тарифна ставка, грн");
        Cell cell31 = rowhead3.createCell(1);
        cell31.setCellValue(driver.getWageRate());
        ///////////////////////////////////////////////////////////////////////////////
        ///ця змінна буде вказувати з якого рядка починати вставляти табличні дані
        int rowToStart = 5;

        Row rowNameTableHectare = sheet.createRow(rowToStart);
                                                                ///3 - це відступ від записів що вище
        Cell cellHectVirobitok = rowNameTableHectare.createCell(SHIFT_FROM_LEFT+2);
        cellHectVirobitok.setCellValue("ГЕКТАРНИЙ ВИРОБІТОК");
        rowToStart++;
        Row rowHeadTableHectare = sheet.createRow(rowToStart);
        ///Записується шапка з таблиці DriverDataHectare
        for(int i=0; i<tableModelHectare.getColumnCount()-TRANSIENT_COLUMN;i++) {
            Cell cell = rowHeadTableHectare.createCell(i+SHIFT_FROM_LEFT);
            cell.setCellValue(tableModelHectare.getColumnName(i));
        }
        rowToStart++;
        ///Записуються дані з таблиці DriverDataHectare
        for(int i=0; i<tableModelHectare.getRowCount();i++) {
            Row rowHect = sheet.createRow(rowToStart);
            for(int j=0; j<tableModelHectare.getColumnCount()-TRANSIENT_COLUMN;j++){
                Cell cell = rowHect.createCell(j+SHIFT_FROM_LEFT);
                cell.setCellValue((String) tableModelHectare.getValueAt(i,j));
            }
            rowToStart++;
        }

        Row rowTotalHectare = sheet.createRow(rowToStart);
        ///Записується сума зароблених грошей за гектарний виробіток
        for(int j=0; j<tableModelTotalHectare.getColumnCount()-TRANSIENT_COLUMN;j++){
            Cell cell = rowTotalHectare.createCell(j+SHIFT_FROM_LEFT);
            cell.setCellValue((String) tableModelTotalHectare.getValueAt(0,j));
        }
        rowToStart+=4;

        Row rowNameHourTable = sheet.createRow(rowToStart-1);
        Cell cellHourVirobitok = rowNameHourTable.createCell(SHIFT_FROM_LEFT+2);
        cellHourVirobitok.setCellValue("ГОДИННИЙ ВИРОБІТОК");

        Row rowHeadTableHour = sheet.createRow(rowToStart);
        ///записується шапка таблиціDriverDataHectare
        for(int i=0; i<tableModelHour.getColumnCount()-TRANSIENT_COLUMN;i++) {
            Cell cell = rowHeadTableHour.createCell(i+SHIFT_FROM_LEFT);
            cell.setCellValue(tableModelHour.getColumnName(i));
        }
        rowToStart++;
        ///записуються дані з DriverDataHectare
        for(int i=0; i<tableModelHour.getRowCount();i++) {
            Row rowHour = sheet.createRow(rowToStart);
            for(int j=0; j<tableModelHour.getColumnCount()-TRANSIENT_COLUMN;j++){
                Cell cell = rowHour.createCell(j+SHIFT_FROM_LEFT);
                cell.setCellValue((String) tableModelHour.getValueAt(i,j));
            }
            rowToStart++;
        }


        Row rowTotalHour = sheet.createRow(rowToStart);
        ///Записується сума зароблених грошей за годинний виробіток
        for(int j=0; j<tableModelTotalHour.getColumnCount()-TRANSIENT_COLUMN;j++){
            Cell cell = rowTotalHour.createCell(j+SHIFT_FROM_LEFT);
            cell.setCellValue((String) tableModelTotalHour.getValueAt(0,j));
        }

        rowToStart+=2;
        Row rowTotal = sheet.createRow(rowToStart);
        ///Записується сума зароблених грошей за гектарний виробіток та за годинний виробіток
        for(int j=0; j<tableModelTotal.getColumnCount()-TRANSIENT_COLUMN;j++){
            Cell cell = rowTotal.createCell(j+SHIFT_FROM_LEFT);
            cell.setCellValue((String) tableModelTotal.getValueAt(0,j));
        }

        for(int i = 0; i<15; i++){
            sheet.autoSizeColumn(i);
        }

        ///прохід по всім
//        for(){
//
//        }


        workbook.write(new FileOutputStream(file));
        workbook.close();
    }


}
