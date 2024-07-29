package com.backend.questify.Controller;

import com.backend.questify.DTO.QuestionDto;
import com.backend.questify.Entity.Question;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	QuestionService questionService;

	@PostMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<QuestionDto>> createQuestion(@RequestBody QuestionDto questionDto,
																	   @RequestParam UUID laboratoryId) {
		ApiResponse<QuestionDto> response = ApiResponse.success(questionService.createQuestion(laboratoryId, questionDto) , HttpStatus.CREATED, "Create Question Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/laboratory")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<List<QuestionDto>>> getQuestions(@RequestParam UUID laboratoryId) {
		ApiResponse<List<QuestionDto>> response = ApiResponse.success(questionService.getQuestions(laboratoryId) , HttpStatus.OK, "Get Questions Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<QuestionDto>> getQuestion(@RequestParam UUID questionId) {
		ApiResponse<QuestionDto> response = ApiResponse.success(questionService.getQuestion(questionId) , HttpStatus.OK, "Get Question Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<QuestionDto>> updateQuestion(@RequestParam UUID questionId, @RequestBody QuestionDto questionDto) {
		ApiResponse<QuestionDto> response = ApiResponse.success(questionService.updateQuestion(questionId, questionDto) , HttpStatus.OK, "Update Question Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteQuestion(@RequestParam UUID questionId) {
		questionService.deleteQuestion(questionId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Question Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}




}
