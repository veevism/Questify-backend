package com.backend.questify.Controller;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

	@Autowired
	LaboratoryService laboratoryService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<LaboratoryDto>> createLaboratory(@RequestBody LaboratoryDto laboratoryDto,
																	   @RequestParam UUID assignmentId) {
		LaboratoryDto laboratoryDto1 = laboratoryService.createLaboratory(assignmentId);
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryDto1 , HttpStatus.CREATED, "Create Laboratory Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/assignment")
	public ResponseEntity<ApiResponse<List<LaboratoryDto>>> getLaboratories(@RequestParam UUID assignmentId) {
		List<LaboratoryDto> laboratoryDtoList = laboratoryService.getLaboratories(assignmentId);
		ApiResponse<List<LaboratoryDto>> response = ApiResponse.success(laboratoryDtoList , HttpStatus.CREATED, "Get Laboratories Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	public ResponseEntity<Void> getLaboratory(@RequestParam UUID laboratoryId) {

		return null;
	}

	@PutMapping("")
	public ResponseEntity<Void> updateLaboratory(@RequestParam UUID laboratoryId) {

		return null;
	}

	@DeleteMapping("")
	public ResponseEntity<Void> deleteLaboratory(@RequestParam UUID laboratoryId) {

		return null;
	}




}
