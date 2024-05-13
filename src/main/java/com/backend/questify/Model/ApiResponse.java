package com.backend.questify.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	private HttpStatus status;
	private String message;
	private String error;
	private T data;


	public static <T> ApiResponse<T> success(T data,HttpStatus status, String message) {
		return new ApiResponse<>(true, status, message, null, data);
	}

	public static <T> ApiResponse<T> success(T data, String message) {
		return new ApiResponse<>(true, HttpStatus.OK,message, null, data );
	}

	public static <T> ApiResponse<T> failure(HttpStatus status, String error, String message) {
		return new ApiResponse<>(false, status,  message, error, null);
	}

	public static <T> ApiResponse<T> failure(String error, String message) {
		return new ApiResponse<>(false, HttpStatus.NOT_FOUND,  message, error, null);
	}

}
