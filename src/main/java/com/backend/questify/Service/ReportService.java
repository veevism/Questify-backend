package com.backend.questify.Service;


import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.Report;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private EntityHelper entityHelper;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public ReportDto getReport(UUID questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + questionId));

        Submission submission = submissionRepository.findByQuestionAndStudent(question, entityHelper.getCurrentUser().getStudent()).orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + questionId));

        Report report = reportRepository.findBySubmission(submission)
                .orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + questionId));
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

    public ReportDto getReportByReportId(UUID reportId) {
        return DtoMapper.INSTANCE.reportToReportDto(reportRepository.findById(reportId).orElseThrow(() -> new ResourceNotFoundException("Report not found for submission id: " + reportId)));
    }
}