package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.TestCase;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Util.DtoMapper;
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

	public TestCaseDto createTestCase(UUID laboratoryId, TestCaseDto testCaseDto) {
		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(laboratoryId);

		Laboratory laboratory = laboratoryResult.orElseThrow(() -> new ResourceNotFoundException("Laboratory not found with Id : " + laboratoryId));

		TestCase createdTestCase = TestCase.builder().input(testCaseDto.getInput())
				.expectedOutput(testCaseDto.getExpectedOutput())
				.build();

		laboratory.addTestCase(createdTestCase);

		testCaseRepository.save(createdTestCase);

		laboratoryRepository.saveAndFlush(laboratory);

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(createdTestCase);
	}

	public List<TestCaseDto> getTestCases(UUID laboratoryId) {

		List<TestCase> testCases = testCaseRepository.findAllByLaboratory_LaboratoryId(laboratoryId);

		if (testCases.isEmpty()) {
			throw new ResourceNotFoundException("Test Cases not found");

		}

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(testCases);
	}

	public TestCaseDto getTestCase(UUID testCaseId) {
		Optional<TestCase> testCaseResult = testCaseRepository.findById(testCaseId);

		TestCase testCase = testCaseResult.orElseThrow(() -> new ResourceNotFoundException("Test Case not found with Id : " + testCaseId));

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase);

	}

	public void deleteTestCase(UUID testCaseId) {
		Optional<TestCase> testCaseResult = testCaseRepository.findById(testCaseId);

		TestCase testCase = testCaseResult.orElseThrow(() -> new ResourceNotFoundException("Test Case not found with Id : " + testCaseId));

		Optional<Laboratory> laboratoryResult = laboratoryRepository.findById(testCase.getLaboratory()
																					  .getLaboratoryId());

		Laboratory laboratory = laboratoryResult.orElseThrow(() -> new ResourceNotFoundException("Laboratory of this test case not found with Id : " + testCase.getLaboratory().getLaboratoryId()));

		laboratory.removeTestCase(testCase);

		laboratoryRepository.save(laboratory);
//		if (testCaseRepository.existsById(testCaseId)) {
//			testCaseRepository.deleteById(testCaseId);
//		} else {
//			throw new ResourceNotFoundException("Test Case not found with Id : " + testCaseId);
//		}
	}

	@Transactional
	public TestCaseDto updateTestCase(UUID testCaseId, TestCaseDto testCaseDto) {
		Optional<TestCase> testCaseResult = testCaseRepository.findById(testCaseId);

		TestCase testCase = testCaseResult.orElseThrow(() -> new ResourceNotFoundException("Test Case not found with Id : " + testCaseId));

		if (testCaseDto.getInput() != null) {
			testCase.setInput(testCaseDto.getInput());
		}

		if (testCaseDto.getExpectedOutput() != null) {
			testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
		}

		testCaseRepository.save(testCase);

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(testCase);
	}
}
