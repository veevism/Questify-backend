package com.backend.questify.Controller;

import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<ReportDto>> getReport(@RequestParam UUID submissionId) {
        ApiResponse<ReportDto> response = ApiResponse.success(reportService.getReportBySubmissionId(submissionId), HttpStatus.OK, "Get Report Successfully");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/laboratory")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getReports(@RequestParam UUID laboratoryId){
        ApiResponse<List<ReportDto>> response = ApiResponse.success(reportService.getReports(laboratoryId), HttpStatus.OK, "Get Reports Successfully");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<ReportDto>> updateReport(@RequestParam UUID submissionId, @RequestBody ReportDto reportDto) {
        ApiResponse<ReportDto> response = ApiResponse.success(reportService.updateReport(submissionId, reportDto), HttpStatus.OK, "Update Report Successfully");
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}