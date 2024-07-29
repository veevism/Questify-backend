package com.backend.questify.Service;

import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.TestCase;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class TestCaseService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private TestCaseRepository testCaseRepository;

	@Autowired
	private EntityHelper entityHelper;

	public TestCaseDto createTestCase(UUID questionId, TestCaseDto testCaseDto) {

		Question existingQuestion = entityHelper.findQuestionById(questionId);

		TestCase createdTestCase = TestCase.builder().input(testCaseDto.getInput())
				.expectedOutput(testCaseDto.getExpectedOutput())
				.build();

		existingQuestion.addTestCase(createdTestCase);

		testCaseRepository.save(createdTestCase);

		questionRepository.saveAndFlush(existingQuestion);

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(createdTestCase);
	}

	public List<TestCaseDto> getTestCases(UUID questionId) {
		return DtoMapper.INSTANCE.testCaseToTestCaseDto(ListIsNotEmptyException.requireNotEmpty(entityHelper.findQuestionById(questionId).getTestCases(), TestCase.class.getSimpleName()));
	}

	public TestCaseDto getTestCase(UUID testCaseId) {
		return DtoMapper.INSTANCE.testCaseToTestCaseDto(entityHelper.findTestCaseById(testCaseId));

	}

	public void deleteTestCase(UUID testCaseId) {

		Question question = entityHelper.findLaboratoryByTestCaseId(testCaseId);

		question.removeTestCase(entityHelper.findTestCaseById(testCaseId));

		questionRepository.save(question);

	}

	@Transactional
	public TestCaseDto updateTestCase(UUID testCaseId, TestCaseDto testCaseDto) {


		TestCase testCase = entityHelper.findTestCaseById(testCaseId);

		if (testCaseDto.getInput() != null) {
			testCase.setInput(testCaseDto.getInput());
		}

		if (testCaseDto.getExpectedOutput() != null) {
			testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
		}

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(testCaseRepository.save(testCase));
	}

	public void deleteTestCases(UUID questionId) {

		Question question = entityHelper.findQuestionById(questionId);

		question.getTestCases().clear();

		questionRepository.save(question);
	}
}
