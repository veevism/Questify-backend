package com.backend.questify.Controller;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

	@Autowired
	LaboratoryService laboratoryService;

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteLaboratory(@RequestParam UUID laboratoryId) {
		laboratoryService.deleteLaboratory(laboratoryId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Laboratorys Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> updateLaboratory(@RequestParam UUID laboratoryId, @RequestBody Laboratory laboratory) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.updateLaboratory(laboratoryId, laboratory), HttpStatus.OK, "Update Laboratorys Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> getLaboratory(@RequestParam UUID laboratoryId) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.getLaboratory(laboratoryId), HttpStatus.OK, "Get Laboratory Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<List<LaboratoryDto>>> getLaboratories() {
		ApiResponse<List<LaboratoryDto>> response = ApiResponse.success(laboratoryService.getLaboratories(), HttpStatus.OK, "Get Laboratories Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> createLaboratory(@RequestBody Laboratory laboratory) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.createLaboratory(laboratory), HttpStatus.CREATED, "Create Laboratory Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/join")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> joinLaboratory(@RequestParam String invitationCode) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.studentJoinLaboratory(invitationCode), HttpStatus.OK, "Join Laboratory Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/remove")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> removeFromLaboratory(@RequestParam UUID laboratoryId, @RequestParam Long studentId) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.removeStudentFromLaboratory(laboratoryId, studentId), HttpStatus.OK, "Remove Student With Id : " + studentId + " Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/assign-question")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> randomAssignQuestion(
			@RequestParam UUID laboratoryId) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.randomAssignQuestion(laboratoryId), HttpStatus.OK, "Assign Question Randomly Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}



}
