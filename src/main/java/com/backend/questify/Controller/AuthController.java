package com.backend.questify.Controller;

import com.backend.questify.DTO.User.UserDto;
import com.backend.questify.DTO.User.UserRequestDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Security.JwtTokenProvider;
import com.backend.questify.Service.ExternalApiService;
import com.backend.questify.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@Autowired
	private ExternalApiService externalApiService;

	//! Todo : Finish login methodology
	@PostMapping("/login")
	public Mono<ResponseEntity<ApiResponse<AuthenticationResponse>>> login(@RequestHeader("Authorization") String authorizationHeader) {
		String tokenA = authorizationHeader.replace("Bearer ", "");
		return externalApiService.getUserInfo(tokenA)
								 .map(userRequest -> {
									 String token = userService.authenticate(userRequest);
									 ApiResponse<AuthenticationResponse> response = ApiResponse.success(new AuthenticationResponse(token), HttpStatus.OK, "Login Successfully");
									 return ResponseEntity.status(response.getStatus()).body(response);
								 })
								 .onErrorResume(e -> {
									 ApiResponse<AuthenticationResponse> response = ApiResponse.failure(
											 String.valueOf(HttpStatus.UNAUTHORIZED), "Error fetching user info: " + e.getMessage());
									 return Mono.just(ResponseEntity.status(response.getStatus()).body(response));
								 });
	}

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@GetMapping("/verify-token")
	public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
		// Extract the token from the "Bearer " prefix
		String token = authorizationHeader.replace("Bearer ", "");

		// Validate the token
		if (jwtTokenProvider.validateToken(token)) {
			// Extract information from the token
			Long userId = jwtTokenProvider.getUserId(token);
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			Map<String, Object> claims = jwtTokenProvider.getClaims(token);

			// Return the extracted information
			return ResponseEntity.ok(Map.of(
					"userId", userId,
					"authorities", authentication.getAuthorities(),
					"claims", claims
			));
		} else {
			return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token"));
		}
	}

	@GetMapping("/profile")
	public ResponseEntity<ApiResponse<UserDto>> GetProfile() {

		UserDto userDto1 = userService.getProfile();
		ApiResponse<UserDto> response = ApiResponse.success(userDto1, HttpStatus.OK, "Get Profile Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/login-manual")
	public ResponseEntity<ApiResponse<AuthenticationResponse>> login(@RequestBody UserRequestDto userRequest) {
		String token = userService.authenticate(userRequest);

		ApiResponse<AuthenticationResponse> response = ApiResponse.success(new AuthenticationResponse(token), HttpStatus.OK, "Get Profile Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@Data
	@AllArgsConstructor
	public static class AuthenticationResponse {
		private String token;
	}

}