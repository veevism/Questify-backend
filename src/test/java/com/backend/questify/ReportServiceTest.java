package com.backend.questify;


import com.backend.questify.DTO.LoggingDto;
import com.backend.questify.DTO.ReportDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.ActionName;
import com.backend.questify.Repository.LoggingRepository;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Service.ReportService;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import com.backend.questify.Util.TestSetupUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private LoggingRepository loggingRepository;

    @Mock
    private EntityHelper entityHelper;

    @InjectMocks
    private ReportService reportService;

    private Professor professor;
    private Student student1;
    private Laboratory laboratory;
    private Question question;
    private Submission submission;
    private Report report1;
    private Report report2;
    private ReportDto reportDto;
    private Logging logging;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor = TestSetupUtil.setupProfessor();
        student1 = TestSetupUtil.setupStudent();
        laboratory = TestSetupUtil.setupLaboratory(professor, student1);
        question = TestSetupUtil.setupQuestion(laboratory, professor);
        submission = TestSetupUtil.setupSubmission(question, student1, professor);

        report1 = TestSetupUtil.setupReport(submission);
        report2 = TestSetupUtil.setupReport(submission);
        report2.setReportId(UUID.randomUUID());

        when(entityHelper.getCurrentUser()).thenReturn(student1.getUser());

        when(reportRepository.findAllBySubmission_Question_Laboratory_LaboratoryId(any(UUID.class)))
                .thenReturn(List.of(report1, report2));
        when(reportRepository.findBySubmission_SubmissionId(any(UUID.class))).thenReturn(Optional.of(report1));
        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        logging = Logging.builder()
                .loggingId(UUID.randomUUID())
                .actionName(ActionName.SWITCH_TAB)
                .report(report1)
                .build();


        when(reportRepository.findBySubmission_SubmissionId(any(UUID.class)))
                .thenReturn(Optional.of(report1));
        when(loggingRepository.save(any(Logging.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

    }

    @Test
    @DisplayName("Should get all reports for a laboratory successfully")
    void getReports_ShouldReturnReportsSuccessfully() {
        List<ReportDto> result = reportService.getReports(laboratory.getLaboratoryId());

        assertNotNull(result);
        assertEquals(2, result.size());

        ReportDto reportDto1 = result.get(0);
        assertEquals(report1.getReportId(), reportDto1.getReportId());
        assertEquals(submission.getSubmissionId(), reportDto1.getSubmission().getSubmissionId());
        assertEquals(student1.getStudentId(), reportDto1.getSubmission().getStudentId());

        ReportDto reportDto2 = result.get(1);
        assertEquals(report2.getReportId(), reportDto2.getReportId());
        assertEquals(submission.getSubmissionId(), reportDto2.getSubmission().getSubmissionId());
        assertEquals(student1.getStudentId(), reportDto2.getSubmission().getStudentId());

        System.out.println("Get Reports Test Passed. Result: " + result);
    }

    @Test
    @DisplayName("Should return report successfully for a given questionId and studentId")
    void getReport_ShouldReturnReportSuccessfully() {
        UUID questionId = question.getQuestionId();
        UUID submissionId = submission.getSubmissionId();

        when(questionRepository.findById(any(UUID.class))).thenReturn(Optional.of(question));
        when(submissionRepository.findByQuestionAndStudent(any(Question.class), any(Student.class))).thenReturn(Optional.of(submission));
        when(reportRepository.findBySubmission(any(Submission.class))).thenReturn(Optional.of(report1));

        ReportDto result = reportService.getReport(questionId);

        assertNotNull(result);
        assertEquals(report1.getReportId(), result.getReportId());
        assertEquals(submissionId, result.getSubmission().getSubmissionId());
        assertEquals(student1.getStudentId(), result.getSubmission().getStudentId());

        System.out.println("Get Report Test Passed. Result: " + result);
    }

    @Test
    @DisplayName("Should update report with given score successfully")
    void updateReport_ShouldUpdateGivenScoreSuccessfully() {
        reportDto = DtoMapper.INSTANCE.reportToReportDto(report1);
        reportDto.setGivenScore(50);

        ReportDto updatedReport = reportService.updateReport(report1.getSubmission().getSubmissionId(), reportDto);

        assertNotNull(updatedReport);
        assertEquals(50, updatedReport.getGivenScore());
        assertEquals(report1.getReportId(), updatedReport.getReportId());
        assertEquals(report1.getSubmission().getSubmissionId(), updatedReport.getSubmission().getSubmissionId());

        System.out.println("Update Report Test Passed. Updated Report: " + updatedReport);
    }

    @Test
    @DisplayName("Should create a log event successfully")
    void logEvent_ShouldCreateLogSuccessfully() {
        Logging loggingDto = new Logging();
        loggingDto.setActionName(ActionName.SWITCH_TAB);

        when(reportRepository.findBySubmission_SubmissionId(any(UUID.class))).thenReturn(Optional.of(report1));
        when(loggingRepository.save(any(Logging.class))).thenAnswer(invocation -> invocation.getArgument(0));

        reportService.logEvent(submission.getSubmissionId(), loggingDto);

        verify(loggingRepository, times(1)).save(any(Logging.class));
        assertEquals(1, report1.getLoggings().size());
        assertEquals(ActionName.SWITCH_TAB, report1.getLoggings().get(0).getActionName());

        System.out.println("Log Event Test Passed.");
    }
}