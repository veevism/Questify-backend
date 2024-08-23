package com.backend.questify.Controller;

import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("")
//    public ReportDto getReports(@RequestParam UUID laboratoryId) {
//        return reportService.getAllReport(submissionId);
//    }

    @PutMapping("")
    public ReportDto updateReport(@RequestParam UUID submissionId, @RequestBody ReportDto reportDto) {
        return reportService.updateReport(submissionId, reportDto);
    }
}