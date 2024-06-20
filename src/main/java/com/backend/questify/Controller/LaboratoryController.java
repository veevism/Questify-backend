package com.backend.questify.Controller;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Entity.Assignment;
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

	@PostMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> createLaboratory(@RequestBody LaboratoryDto laboratoryDto,
																	   @RequestParam UUID assignmentId) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.createLaboratory(assignmentId, laboratoryDto) , HttpStatus.CREATED, "Create Laboratory Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}



	@GetMapping("/assignment")
	public ResponseEntity<ApiResponse<List<LaboratoryDto>>> getLaboratories(@RequestParam UUID assignmentId) {
		ApiResponse<List<LaboratoryDto>> response = ApiResponse.success(laboratoryService.getLaboratories(assignmentId) , HttpStatus.OK, "Get Laboratories Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<LaboratoryDto>> getLaboratory(@RequestParam UUID laboratoryId) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.getLaboratory(laboratoryId) , HttpStatus.OK, "Get Laboratory Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<LaboratoryDto>> updateLaboratory(@RequestParam UUID laboratoryId, @RequestBody LaboratoryDto laboratory) {
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryService.updateLaboratory(laboratoryId, laboratory) , HttpStatus.OK, "Update Laboratory Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteLaboratory(@RequestParam UUID laboratoryId) {
		laboratoryService.deleteLaboratory(laboratoryId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Laboratory Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}




}
