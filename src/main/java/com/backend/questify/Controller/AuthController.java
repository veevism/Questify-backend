package com.backend.questify.Controller;

import com.backend.questify.DTO.UserRequestDto;
import com.backend.questify.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@Data
	@AllArgsConstructor
	public static class AuthenticationResponse {
		private String token;
	}
}