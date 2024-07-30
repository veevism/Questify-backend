package com.backend.questify.Service;


import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Entity.Report;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Util.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportDto getReportBySubmissionId(Long submissionId) {
        Report report = reportRepository.findBySubmission_SubmissionId(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + submissionId));
        return DtoMapper.INSTANCE.reportToReportDto(report);
    }

    @Transactional
    public ReportDto updateReport(Long reportId, ReportDto reportDto) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found with id: " + reportId));

        if (reportDto.getGivenScore() != null) {
            report.setGivenScore(reportDto.getGivenScore());
        }

        return DtoMapper.INSTANCE.reportToReportDto(reportRepository.save(report));
    }
}