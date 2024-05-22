package com.backend.questify.Controller;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.DTO.UserDto;
import com.backend.questify.DTO.UserRequestDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Security.security.JwtTokenProvider;
import com.backend.questify.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody UserRequestDto userRequest) {
		String token = userService.authenticate(userRequest);
		return ResponseEntity.ok(new AuthenticationResponse(token));
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
	public ResponseEntity<ApiResponse<Void>> GetProfile() {
userService.getProfile();

		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Get Assignment Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);
	}


		@Data
	@AllArgsConstructor
	public static class AuthenticationResponse {
		private String token;
	}
}