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
    public ReportDto getReport(@RequestParam UUID submissionId) {
        return reportService.getReportBySubmissionId(submissionId);
    }

    @GetMapping("/laboratory")
    public ResponseEntity<ApiResponse<List<ReportDto>>> getReports(@RequestParam UUID laboratoryId){
        ApiResponse<List<ReportDto>> response = ApiResponse.success(reportService.getReports(laboratoryId), HttpStatus.OK, "Get Reports Successfully");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

//    @GetMapping("")
//    public ReportDto getReports(@RequestParam UUID laboratoryId) {
//        return reportService.getAllReport(submissionId);
//    }

    @PutMapping("")
    public ReportDto updateReport(@RequestParam UUID submissionId, @RequestBody ReportDto reportDto) {
        return reportService.updateReport(submissionId, reportDto);
    }
}