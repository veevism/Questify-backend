package com.backend.questify.Service;


import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.Report;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Util.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public ReportDto getReportBySubmissionId(UUID submissionId) {
        Report report = reportRepository.findBySubmission_SubmissionId(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + submissionId));
        return DtoMapper.INSTANCE.reportToReportDto(report);
    }

    public List<ReportDto> getReports(UUID laboratoryId) {
        List<Report> reports = ListIsNotEmptyException.requireNotEmpty(reportRepository.findAllBySubmission_Question_Laboratory_LaboratoryId(laboratoryId), Laboratory.class.getSimpleName());
        return DtoMapper.INSTANCE.reportToReportDto(reports);

    }

    @Transactional
    public ReportDto updateReport(UUID submissionId, ReportDto reportDto) {
        Report report = reportRepository.findBySubmission_SubmissionId(submissionId)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + submissionId));

        if (reportDto.getGivenScore() != null) {
            report.setGivenScore(reportDto.getGivenScore());
        }

        // check submitTime in submission and reportDto
        // add case givenScore can't be more than maxScore
        // change every id to UUID

        return DtoMapper.INSTANCE.reportToReportDto(reportRepository.save(report));
    }
}