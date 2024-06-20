package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.TestCase;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class TestCaseService {

	@Autowired
	private LaboratoryRepository laboratoryRepository;

	@Autowired
	private TestCaseRepository testCaseRepository;

	@Autowired
	private EntityHelper entityHelper;

	public TestCaseDto createTestCase(UUID laboratoryId, TestCaseDto testCaseDto) {

		Laboratory laboratory = entityHelper.findLaboratoryById(laboratoryId);

		TestCase createdTestCase = TestCase.builder().input(testCaseDto.getInput())
				.expectedOutput(testCaseDto.getExpectedOutput())
				.build();

		laboratory.addTestCase(createdTestCase);

		testCaseRepository.save(createdTestCase);

		laboratoryRepository.saveAndFlush(laboratory);

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(createdTestCase);
	}

	public List<TestCaseDto> getTestCases(UUID laboratoryId) {
		return DtoMapper.INSTANCE.testCaseToTestCaseDto(ListIsNotEmptyException.requireNotEmpty(entityHelper.findLaboratoryById(laboratoryId).getTestCases(), "Test Case"));
	}

	public TestCaseDto getTestCase(UUID testCaseId) {
		return DtoMapper.INSTANCE.testCaseToTestCaseDto(entityHelper.findTestCaseById(testCaseId));

	}

	public void deleteTestCase(UUID testCaseId) {

		Laboratory laboratory = entityHelper.findLaboratoryByTestCaseId(testCaseId);

		laboratory.removeTestCase(entityHelper.findTestCaseById(testCaseId));

		laboratoryRepository.save(laboratory);

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
}
