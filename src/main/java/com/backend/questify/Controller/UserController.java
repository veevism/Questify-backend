package com.backend.questify.Controller;

import com.backend.questify.DTO.User.UserDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;



//
//	@PostMapping("/")
//	public ResponseEntity<User> createUser(@RequestBody User user) {
//		User createdUser = userService.createUser(user);
//		return ResponseEntity.ok(createdUser);
//	}
//
//	@GetMapping("/{userId}")
//	public ResponseEntity<User> getUserById(@PathVariable Long userId) {
//		return userService.getUserById(userId)
//						  .map(ResponseEntity::ok)
//						  .orElseGet(() -> ResponseEntity.notFound().build());
//	}
//
//	@GetMapping("/username/{userName}")
//	public ResponseEntity<User> getUserByUsername(@PathVariable String userName) {
//		return userService.getUserByUserName(userName)
//						  .map(ResponseEntity::ok)
//						  .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + userName));
//	}
//
//	@GetMapping("/")
//	public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
//		List<UserDto> users = DtoMapper.INSTANCE.userToUserDto(userService.getAllUsers());
//		ApiResponse<List<UserDto>> response = ApiResponse.success(users, HttpStatus.OK, "Get All User Successfully" );
//		return ResponseEntity.status(response.getStatus()).body(response);
//	}
//
//	@PutMapping("/{userId}")
//	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
//		try {
//			User updatedUser = userService.updateUser(userId, userDetails);
//			return ResponseEntity.ok(updatedUser);
//		} catch (RuntimeException e) {
//			return ResponseEntity.notFound().build();
//		}
//	}
//
//	@DeleteMapping("/{userId}")
//	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//		userService.deleteUser(userId);
//		return ResponseEntity.ok().build();
//	}
}