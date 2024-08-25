package com.backend.questify;

import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ConflictException;
import com.backend.questify.Model.SubmissionStatus;
import com.backend.questify.Model.SubmitStatus;
import com.backend.questify.Repository.ReportRepository;
import com.backend.questify.Repository.SubmissionRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Repository.TestCaseResultRepository;
import com.backend.questify.Service.SubmissionService;
import com.backend.questify.Util.EntityHelper;
import com.backend.questify.Util.TestSetupUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SubmissionServiceTest {


    @Mock
    private SubmissionRepository submissionRepository;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private EntityHelper entityHelper;

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private TestCaseResultRepository testCaseResultRepository;

    @InjectMocks
    private SubmissionService submissionService;

    private Professor professor;
    private Student student1;
    private Laboratory laboratory;
    private Question question;
    private Submission submission;
    private Report report;
    private TestCase testCase1;
    private TestCase testCase2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor = TestSetupUtil.setupProfessor();
        student1 = TestSetupUtil.setupStudent();
        laboratory = TestSetupUtil.setupLaboratory(professor, student1);
        question = TestSetupUtil.setupQuestion(laboratory, professor);
        submission = TestSetupUtil.setupSubmission(question, student1, professor);
        report = TestSetupUtil.setupReport(submission);

        testCase1 = TestSetupUtil.setupTestCase1(question);
        testCase2 = TestSetupUtil.setupTestCase2(question);

        TestCaseResult testCaseResult1 = TestSetupUtil.setupTestCaseResult1(submission, testCase1);
        TestCaseResult testCaseResult2 = TestSetupUtil.setupTestCaseResult2(submission, testCase2);
        submission.setTestCaseResults(new ArrayList<>(List.of(testCaseResult1, testCaseResult2)));

        when(entityHelper.getCurrentUser()).thenReturn(student1.getUser());
        when(entityHelper.getCurrentUserId()).thenReturn(student1.getStudentId());
        when(entityHelper.findQuestionById(any(UUID.class))).thenReturn(question);
        when(entityHelper.findStudentById(any(Long.class))).thenReturn(student1);
    }

    @Test
    @DisplayName("Should create and retrieve a submission with a valid question ID")
    void getAndCreateSubmission_ShouldCreateAndRetrieveSubmissionSuccessfully() throws Exception {
        when(submissionRepository.findByQuestionAndStudent(any(Question.class), any(Student.class)))
                .thenReturn(Optional.empty());

        when(submissionRepository.save(any(Submission.class))).thenAnswer(invocation -> {
            Submission submission = invocation.getArgument(0);
            submission.prePersist();
            return submission;
        });        when(reportRepository.save(any(Report.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubmissionDto result = submissionService.getAndCreateSubmission(question.getQuestionId());

        assertNotNull(result);
        assertEquals(student1.getStudentId(), result.getStudentId());
        assertEquals(submission.getCodeSnippets(), result.getCodeSnippets());
        assertEquals(SubmissionStatus.ACTIVE, result.getStatus());
        assertEquals(0, result.getTestCaseResults().size());

        System.out.println("Get And Create Submission Test Passed. Result: " + result);
    }


    @Test
    @DisplayName("Should update a submission with valid data")
    void updateSubmission_ShouldUpdateSubmissionSuccessfully() {
        UUID questionId = question.getQuestionId();
        String language = "Java";
        String updatedCode = "public class Main { public static void main(String[] args) { System.out.println(\"Updated code for Java\"); } }";

        when(submissionRepository.findByQuestionAndStudent(any(Question.class), any(Student.class)))
                .thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubmissionDto result = submissionService.updateSubmissionContent(questionId, language, updatedCode, SubmissionStatus.ACTIVE);

        assertNotNull(result);
        assertEquals(submission.getSubmissionId(), result.getSubmissionId());
        assertEquals(updatedCode, result.getCodeSnippets().get(language));
        assertEquals(SubmissionStatus.ACTIVE, result.getStatus());

        System.out.println("Update Submission Test Passed. Result: " + result);
    }

    @Test
    @DisplayName("Should throw ConflictException when revisiting an already completed submission")
    void getAndCreateSubmission_ShouldThrowConflictExceptionWhenSubmissionIsInactive() {
        submission.setStatus(SubmissionStatus.INACTIVE);
        when(submissionRepository.findByQuestionAndStudent(any(Question.class), any(Student.class)))
                .thenReturn(Optional.of(submission));

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            submissionService.getAndCreateSubmission(question.getQuestionId());
        });

        assertEquals("Submission is marked as inactive. You have already completed this submission.", exception.getMessage());

        System.out.println("Get And Create Submission Test Failed as Expected. Exception: " + exception.getMessage());
    }


    @Test
    @DisplayName("Should execute submission and return mocked test case results")
    void executeSubmission_ShouldReturnMockedTestCaseResults() {
        // Mock repository responses
        when(submissionRepository.findByQuestionAndStudent(any(Question.class), any(Student.class)))
                .thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(testCaseRepository.findAllByQuestion(any(Question.class)))
                .thenReturn(List.of(testCase1, testCase2));

        when(testCaseResultRepository.save(any(TestCaseResult.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SubmissionDto result = submissionService.executeSubmission(question.getQuestionId(), "JavaScript");

        assertNotNull(result);

        assertEquals(2, result.getTestCaseResults().size(), "The size of testCaseResults should be 2");
        assertEquals("NOT PASS", result.getTestCaseResults().get(0).getResult());
        assertEquals("NOT PASS", result.getTestCaseResults().get(1).getResult());

        System.out.println("Execute Submission Test Passed. Result: " + result);
    }

    @Test
    @DisplayName("Should submit submission and set status to INACTIVE")
    void submitSubmission_ShouldSubmitSuccessfully() {
        when(submissionRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(submission));

        when(reportRepository.findBySubmission_SubmissionId(any(UUID.class)))
                .thenReturn(Optional.of(report));


        submission.prePersist();

        when(submissionRepository.save(any(Submission.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));


        SubmissionDto result = submissionService.submitSubmission(submission.getSubmissionId());


        assertNotNull(result);
        assertEquals(SubmissionStatus.INACTIVE, result.getStatus());
        assertEquals(SubmitStatus.ON_TIME, report.getSubmitStatus(), "Submit status should be ON_TIME or LATE");

        System.out.println("Submit Submission Test Passed. Result: " + result);
    }


}
