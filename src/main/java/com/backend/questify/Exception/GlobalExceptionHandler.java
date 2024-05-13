package com.backend.questify.Exception;

import com.backend.questify.Model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ApiResponse<Object> response = ApiResponse.failure(HttpStatus.NOT_FOUND,  ex.getClass().getSimpleName(), ex.getMessage());
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleException(Exception ex) {
		ApiResponse<Object> response = ApiResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, ex.getClass().getSimpleName(), ex.getMessage());
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	// Additional handlers can be added here
}