package com.backend.questify.Service;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.TestCase;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Util.DtoMapper;
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

		laboratoryRepository.save(laboratory);

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(createdTestCase);
	}

	public List<TestCaseDto> getTestCases(UUID laboratoryId) {

		List<TestCase> testCases = testCaseRepository.findAllByLaboratory_LaboratoryId(laboratoryId);

		if (testCases.isEmpty()) {
			throw new ResourceNotFoundException("Test Cases not found");

		}

		return DtoMapper.INSTANCE.testCaseToTestCaseDto(testCases);
	}
}
