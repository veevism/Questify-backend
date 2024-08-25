package com.backend.questify;

import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Service.TestCaseService;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestCaseServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private TestCaseRepository testCaseRepository;

    @Mock
    private EntityHelper entityHelper;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private TestCaseService testCaseService;

    private Professor professor;
    private Student student1;
    private Laboratory laboratory;
    private Question question;
    private TestCase testCase1;
    private TestCase testCase2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor = TestSetupUtil.setupProfessor();
        student1 = TestSetupUtil.setupStudent();
        laboratory = TestSetupUtil.setupLaboratory(professor, student1);
        question = TestSetupUtil.setupQuestion(laboratory, professor);
        testCase1 = TestSetupUtil.setupTestCase1(question);
        testCase2 = TestSetupUtil.setupTestCase2(question);


        when(entityHelper.getCurrentUser()).thenReturn(student1.getUser());

    }

    @Test
    @DisplayName("Should create test case successfully")
    void createTestCase_ShouldCreateTestCaseSuccessfully() {
        when(entityHelper.findQuestionById(any(UUID.class))).thenReturn(question);
        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase1);
        when(dtoMapper.testCaseToTestCaseDto(any(TestCase.class))).thenReturn(DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase1));

        TestCaseDto result = testCaseService.createTestCase(question.getQuestionId(), DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase1));

        assertNotNull(result);
        assertEquals(testCase1.getInput(), result.getInput());
        assertEquals(testCase1.getExpectedOutput(), result.getExpectedOutput());
        verify(testCaseRepository, times(1)).save(any(TestCase.class));
        verify(questionRepository, times(1)).saveAndFlush(any(Question.class));

        System.out.println("Create Test Case Test Passed. Result: " + result);
    }

    @Test
    @DisplayName("Should get all test cases for a question")
    void getTestCases_ShouldReturnTestCasesSuccessfully() {

        question.addTestCase(testCase1);
        question.addTestCase(testCase2);

        when(entityHelper.findQuestionById(any(UUID.class))).thenReturn(question);
        when(dtoMapper.testCaseToTestCaseDto(any(TestCase.class))).thenReturn(DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase1));

        List<TestCaseDto> result = testCaseService.getTestCases(question.getQuestionId());

        System.out.println(result);
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testCase1.getInput(), result.get(0).getInput());
        assertEquals(testCase1.getExpectedOutput(), result.get(0).getExpectedOutput());

        verify(entityHelper, times(1)).findQuestionById(any(UUID.class));

        System.out.println("Get Test Cases Test Passed. Result: " + result);
    }

//    @Test
//    @DisplayName("Should delete a test case successfully")
//    void deleteTestCase_ShouldDeleteTestCaseSuccessfully() {
//        when(entityHelper.findTestCaseById(any(UUID.class))).thenReturn(testCase1);
//        when(entityHelper.findLaboratoryByTestCaseId(any(UUID.class))).thenReturn(question);
//
//        testCaseService.deleteTestCase(testCase1.getTestCaseId());
//
//        verify(testCaseRepository, times(1)).deleteById(testCase1.getTestCaseId());
//        verify(questionRepository, times(1)).save(any(Question.class));
//
//        System.out.println("Delete Test Case Test Passed.");
//    }

    @Test
    @DisplayName("Should update test case successfully")
    void updateTestCase_ShouldUpdateTestCaseSuccessfully() {
        when(entityHelper.findTestCaseById(any(UUID.class))).thenReturn(testCase1);
        when(testCaseRepository.save(any(TestCase.class))).thenReturn(testCase1);
        when(dtoMapper.testCaseToTestCaseDto(any(TestCase.class))).thenReturn(DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase1));

        TestCaseDto updatedDto = DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase1);
        updatedDto.setInput("15, 20");
        updatedDto.setExpectedOutput("300");

        TestCaseDto result = testCaseService.updateTestCase(testCase1.getTestCaseId(), updatedDto);

        assertNotNull(result);
        assertEquals("15, 20", result.getInput());
        assertEquals("300", result.getExpectedOutput());
        verify(testCaseRepository, times(1)).save(any(TestCase.class));

        System.out.println("Update Test Case Test Passed. Result: " + result);
    }
}
