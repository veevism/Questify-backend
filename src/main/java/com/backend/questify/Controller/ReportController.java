package com.backend.questify.Controller;

import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("")
    public ReportDto getReport(@RequestParam Long submissionId) {
        return reportService.getReportBySubmissionId(submissionId);
    }

    @PutMapping("")
    public ReportDto updateReport(@RequestParam Long reportId, @RequestBody ReportDto reportDto) {
        return reportService.updateReport(reportId, reportDto);
    }
}