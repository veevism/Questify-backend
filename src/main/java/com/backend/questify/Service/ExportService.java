package com.backend.questify.Service;

import com.backend.questify.DTO.LoggingDto;
import com.backend.questify.DTO.ReportDto;
import com.backend.questify.DTO.StudentDto;
import com.backend.questify.DTO.TestCaseResultDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Util.EntityHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ExportService {

    @Autowired
    private EntityHelper entityHelper;

    @Autowired
    private ReportService reportService;

    public ResponseEntity<byte[]> exportReport(UUID laboratoryId) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        createReportsSheet(workbook, laboratoryId);
        createProfessorSheet(workbook, laboratoryId);
        createStudentsSheet(workbook, laboratoryId);
        createQuestionsSheet(workbook, laboratoryId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        byte[] excelBytes = out.toByteArray();
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=student_report_%s.xlsx", laboratoryId.toString()));

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
        return style;
    }

    private CellStyle createTextWrapStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        return style;
    }

    private void applyTextWrapAndAutoWidth(Sheet sheet, int numberOfColumns) {
        for (int i = 0; i < numberOfColumns; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createReportsSheet(Workbook workbook, UUID laboratoryId) {
        Sheet sheet = workbook.createSheet("Reports");
        List<ReportDto> reports = reportService.getReports(laboratoryId);
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle textStyle = createTextWrapStyle(workbook);

        String[] headers = {
                "Report ID", "Submission ID", "Student ID", "Student Email", "Created At", "Updated At", "Status", "Java Snippet",
                "C Snippet", "JavaScript Snippet", "Python Snippet", "Test Cases", "Loggings", "Given Score", "Max Score",
                "Start Time", "Submit Time", "Submit Status"
        };
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (ReportDto report : reports) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(report.getReportId().toString());
            row.createCell(1).setCellValue(report.getSubmission().getSubmissionId().toString());
            row.createCell(2).setCellValue(report.getSubmission().getStudentId());
            row.createCell(3).setCellValue(report.getSubmission().getStudent().getEmail());
            row.createCell(4).setCellValue(report.getSubmission().getCreatedAt().toString());
            row.createCell(5).setCellValue(report.getSubmission().getUpdatedAt().toString());
            row.createCell(6).setCellValue(report.getSubmission().getStatus().toString());
            row.createCell(7).setCellValue(report.getSubmission().getCodeSnippets().get("Java"));
            row.createCell(8).setCellValue(report.getSubmission().getCodeSnippets().get("C"));
            row.createCell(9).setCellValue(report.getSubmission().getCodeSnippets().get("JavaScript"));
            row.createCell(10).setCellValue(report.getSubmission().getCodeSnippets().get("Python"));
            row.createCell(11).setCellValue(formatTestCaseResults(report.getSubmission().getTestCaseResults()));
            row.createCell(12).setCellValue(formatLoggings(report.getLoggings()));
            row.createCell(13).setCellValue(report.getGivenScore() != null ? report.getGivenScore().toString() : "N/A");
            row.createCell(14).setCellValue(report.getMaxScore());
            row.createCell(15).setCellValue(report.getStartTime().toString());
            row.createCell(16).setCellValue(report.getSubmitTime() != null ? report.getSubmitTime().toString() : "N/A");
            row.createCell(17).setCellValue(report.getSubmitStatus().toString());
        }
        applyTextWrapAndAutoWidth(sheet, headers.length);
    }

    private void createProfessorSheet(Workbook workbook, UUID laboratoryId) {
        Sheet sheet = workbook.createSheet("Professor");
        Professor professor = entityHelper.findLaboratoryById(laboratoryId).getProfessor();
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle textStyle = createTextWrapStyle(workbook);

        String[] profHeaders = {"Professor ID", "Display Name", "Email", "First Name", "Last Name"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < profHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(profHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        Row row = sheet.createRow(1);
        row.createCell(0).setCellValue(professor.getProfessorId().toString());
        row.createCell(1).setCellValue(professor.getUser().getDisplayName());
        row.createCell(2).setCellValue(professor.getUser().getEmail());
        row.createCell(3).setCellValue(professor.getUser().getFirstName_EN());
        row.createCell(4).setCellValue(professor.getUser().getLastName_EN());

        applyTextWrapAndAutoWidth(sheet, profHeaders.length);
    }

    private void createStudentsSheet(Workbook workbook, UUID laboratoryId) {
        Sheet sheet = workbook.createSheet("Students");
        List<Student> students = entityHelper.findLaboratoryById(laboratoryId).getStudents();
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle textStyle = createTextWrapStyle(workbook);

        String[] studentHeaders = {"Student ID", "Email", "Display Name", "First Name", "Last Name"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < studentHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(studentHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Student student : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getStudentId());
            row.createCell(1).setCellValue(student.getUser().getEmail());
            row.createCell(2).setCellValue(student.getUser().getDisplayName());
            row.createCell(3).setCellValue(student.getUser().getFirstName_EN());
            row.createCell(4).setCellValue(student.getUser().getLastName_EN());
        }

        applyTextWrapAndAutoWidth(sheet, studentHeaders.length);
    }

    private void createQuestionsSheet(Workbook workbook, UUID laboratoryId) {
        Sheet sheet = workbook.createSheet("Questions");
        List<Question> questions = entityHelper.findQuestionsByLaboratory(entityHelper.findLaboratoryById(laboratoryId));
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle textStyle = createTextWrapStyle(workbook);

        String[] questionHeaders = {"Question ID", "Title", "Problem Statement"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < questionHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(questionHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Question question : questions) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(question.getQuestionId().toString());
            row.createCell(1).setCellValue(question.getTitle());
            row.createCell(2).setCellValue(question.getProblemStatement());
        }

        applyTextWrapAndAutoWidth(sheet, questionHeaders.length);
    }

    private String formatTestCaseResults(List<TestCaseResultDto> testCaseResults) {
        StringBuilder sb = new StringBuilder();
        for (TestCaseResultDto result : testCaseResults) {
            sb.append("Input: ").append(result.getTestCase().getInput())
                    .append(", Expected: ").append(result.getTestCase().getExpectedOutput())
                    .append(", Actual: ").append(result.getActualOutput())
                    .append(", Result: ").append(result.getResult()).append(" | ");
        }
        return sb.toString();
    }

    private String formatLoggings(List<LoggingDto> loggings) {
        StringBuilder sb = new StringBuilder();
        for (LoggingDto log : loggings) {
            sb.append("Action: ").append(log.getActionName())
                    .append(", Time: ").append(log.getTimeStamp()).append(" | ");
        }
        return sb.toString();
    }
}
