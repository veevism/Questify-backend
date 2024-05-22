package com.backend.questify.Service;

import com.backend.questify.DTO.StudentDto;
import com.backend.questify.DTO.UserDto;
import com.backend.questify.DTO.UserRequestDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.UserRepository;
import com.backend.questify.Security.security.JwtTokenProvider;
import com.backend.questify.Util.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
									 .enrollmentYear(null)
									 .major(null)
									 .build();
			user.setStudent(student);
		} else if (user.getRole() == Role.ProfAcc) {
			Professor professor = Professor.builder()
										   .professorId(userId)
										   .user(user)
										   .faculty(null)
										   .department(null)
										   .build();
			user.setProfessor(professor);
		}

		return userRepository.save(user);
	}
}