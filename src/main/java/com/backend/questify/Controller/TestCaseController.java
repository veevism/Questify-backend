package com.backend.questify.Controller;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.TestCaseRepository;
import com.backend.questify.Service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/testcase")
public class TestCaseController {
	@Autowired
	private TestCaseService testCaseService;
	@PostMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<TestCaseDto>> createTestCase(@RequestParam UUID laboratoryId,
																   @RequestBody TestCaseDto testCaseDto) {
		ApiResponse<TestCaseDto> response = ApiResponse.success(testCaseService.createTestCase(laboratoryId, testCaseDto), HttpStatus.OK, "Create Test Case Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/laboratory")
	public ResponseEntity<ApiResponse<List<TestCaseDto>>> getTestCases(@RequestParam UUID laboratoryId) {
		ApiResponse<List<TestCaseDto>> response = ApiResponse.success(testCaseService.getTestCases(laboratoryId), HttpStatus.OK, "Get All Test Case Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<TestCaseDto>> getTestCase(@RequestParam UUID testCaseId) {
		ApiResponse<TestCaseDto> response = ApiResponse.success(testCaseService.getTestCase(testCaseId), HttpStatus.OK, "Get Test Case Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<TestCaseDto>> updateTestCase(@RequestParam UUID testCaseId, @RequestBody TestCaseDto testCaseDto) {
		ApiResponse<TestCaseDto> response = ApiResponse.success(testCaseService.updateTestCase(testCaseId, testCaseDto), HttpStatus.OK, "Update Test Case Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteTestCase(@RequestParam UUID testCaseId) {
		testCaseService.deleteTestCase(testCaseId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Test Case Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}


}
