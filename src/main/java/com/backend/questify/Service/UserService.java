package com.backend.questify.Service;

import com.backend.questify.DTO.User.UserDto;
import com.backend.questify.DTO.User.UserRequestDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Exception.UnauthorizedAccessException;
import com.backend.questify.Exception.UserNotAuthenticatedException;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.UserRepository;
import com.backend.questify.Security.JwtTokenProvider;
import com.backend.questify.Util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	public UserDto getProfile() {
		Long userId = getCurrentUserId();
 
		Optional<User> userResult = userRepository.findById(getCurrentUserId());
		User user = userResult.orElseThrow(() -> new ResourceNotFoundException("User Not Found By Access Token"));

		return DtoMapper.INSTANCE.userToUserDto(user);
	}

	public String authenticate(UserRequestDto userRequest) {
		Optional<User> existingUser = userRepository.findById(Long.valueOf(userRequest.getStudent_id()));
		User user;

		if (existingUser.isPresent()) {
			user = existingUser.get();
		} else {
			user = createUser(userRequest);
		}

		return jwtTokenProvider.createToken(user.getUserId(), user.getRole());
	}

	private User createUser(UserRequestDto userRequest) {
		Long userId = Long.valueOf(userRequest.getStudent_id());

		User user = User.builder()
						.userId(userId)
						.userName(userRequest.getCmuitaccount_name())
						.email(userRequest.getCmuitaccount())
						.firstName_EN(userRequest.getFirstname_EN())
						.lastName_EN(userRequest.getLastname_EN())
						.displayName(userRequest.getFirstname_EN() + " " + userRequest.getLastname_EN())
						.organization_name_EN(userRequest.getOrganization_name_EN())
						.role(Role.valueOf(userRequest.getItaccounttype_id()))
						.build();

		if (user.getRole() == Role.StdAcc) {
			Student student = Student.builder()
									 .studentId(userId)
									 .user(user)
									 .build();
			user.setStudent(student);
		} else if (user.getRole() == Role.ProfAcc) {
			Professor professor = Professor.builder()
										   .professorId(userId)
										   .user(user)
										   .build();
			user.setProfessor(professor);
		}



		return userRepository.save(user);
	}

	public Long getCurrentUserId() throws UserNotAuthenticatedException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			try {
				System.out.println(userDetails.getUsername());
				return Long.valueOf(userDetails.getUsername()); // Assuming the username is the userId
			} catch (NumberFormatException e) {
				throw new UserNotAuthenticatedException("Invalid user ID format.");
			}
		}
		throw new UnauthorizedAccessException("User is not authenticated.");
	}


	public User getCurrentUser() {
		Long userId = getCurrentUserId();
		return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
	}
}