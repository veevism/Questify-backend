package com.backend.questify.Controller;

import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {

	@Autowired
	LaboratoryService laboratoryService;

	@PostMapping("")
	public ResponseEntity<ApiResponse<LaboratoryDto>> createLaboratory() {
		LaboratoryDto laboratoryDto1 = laboratoryService.createLaboratory();
		ApiResponse<LaboratoryDto> response = ApiResponse.success(laboratoryDto1 , HttpStatus.CREATED, "Create Classrooms Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}


}
