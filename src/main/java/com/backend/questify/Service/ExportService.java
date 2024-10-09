package com.backend.questify.Service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ExportService {
    public ResponseEntity<byte[]> exportReport(UUID laboratoryId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Student Reports");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Student ID");
        headerRow.createCell(1).setCellValue("Student Name");
        headerRow.createCell(2).setCellValue("Submission ID");
        headerRow.createCell(3).setCellValue("Status");
        headerRow.createCell(4).setCellValue("Test Case Input");
        headerRow.createCell(5).setCellValue("Expected Output");
        headerRow.createCell(6).setCellValue("Actual Output");
        headerRow.createCell(7).setCellValue("Result");

        Object[][] data = {
                {642115555, "Veevi Nveee", "d9427eae-4d0c-4aa0-b2ab-2e828b39710c", "ACTIVE", "10 50", "500", "Welcome To Questify Ja from Java", "NOT PASS"},
                {642115555, "Veevi Nveee", "d9427eae-4d0c-4aa0-b2ab-2e828b39710c", "ACTIVE", "10 50", "500", "Welcome To Questify Ja from Java", "NOT PASS"},
                {642115555, "Veevi Nveee", "d9427eae-4d0c-4aa0-b2ab-2e828b39710c", "ACTIVE", "50 50", "2500", "Welcome To Questify Ja from Java", "NOT PASS"},
                {642115555, "Veevi Nveee", "d9427eae-4d0c-4aa0-b2ab-2e828b39710c", "ACTIVE", "100 25", "2500", "Welcome To Questify Ja from Java", "NOT PASS"}
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : rowData) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        byte[] excelBytes = out.toByteArray();
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=student_report.xlsx");

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);

    }
}
