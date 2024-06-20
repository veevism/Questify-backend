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
import com.backend.questify.Util.EntityHelper;
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
	private JwtTokenProvider jwtTokenProvider;


	@Autowired
	private EntityHelper entityHelper;

	public UserDto getProfile() {
		Optional<User> userResult = userRepository.findById((entityHelper.getCurrentUserId()));
		User user = userResult.orElseThrow(() -> new ResourceNotFoundException("User Not Found By Access Token"));

		return DtoMapper.INSTANCE.userToUserDto(user);
	}

	public String authenticate(UserRequestDto userRequest) {
		User user = userRepository.findById(Long.valueOf(userRequest.getStudent_id())).orElse(createUser(userRequest));

		return jwtTokenProvider.createToken(user.getUserId(), user.getRole());
	}

	public User createUser(UserRequestDto userRequest) {
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

}