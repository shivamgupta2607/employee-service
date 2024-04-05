package com.shivam.employee.service.impl;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelProcessor {
    public static void main(String[] args) {
        String excelFile = "input.xlsx";
        String outputExcelFile = "output.xlsx";

        try (FileInputStream fis = new FileInputStream(excelFile);
             XSSFWorkbook workbook = new XSSFWorkbook(fis);
             FileOutputStream fos = new FileOutputStream(outputExcelFile)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming data is on the first sheet

            // Process header row
            Row headerRow = sheet.getRow(0);
            Cell incrementCell = headerRow.createCell(headerRow.getLastCellNum(), CellType.STRING);
            incrementCell.setCellValue("Increment %");

            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                double salary = row.getCell(3).getNumericCellValue();
                double increment = row.getCell(4).getNumericCellValue();
                double newSalary = salary * (1 + increment / 100);

                Cell newIncrementCell = row.createCell(row.getLastCellNum(), CellType.NUMERIC);
                newIncrementCell.setCellValue(increment);

                Cell newSalaryCell = row.createCell(row.getLastCellNum(), CellType.NUMERIC);
                newSalaryCell.setCellValue(newSalary);
            }

            workbook.write(fos);
            System.out.println("File processed successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
