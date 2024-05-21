package com.backend.questify.Service;

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
			Optional<User> existingUser = userRepository.findByUserName(userRequest.getCmuitaccount_name());
			User user;

			if (existingUser.isPresent()) {
				user = existingUser.get();
			} else {
				user = createUser(userRequest);
			}

			return jwtTokenProvider.createToken(user.getUserName(), user.getRole());
		}

		private User createUser(UserRequestDto userRequest) {
			User user = User.builder()
							.userName(userRequest.getCmuitaccount_name())
							.email(userRequest.getCmuitaccount())
							.firstName_EN(userRequest.getFirstname_EN())
							.lastName_EN(userRequest.getLastname_EN())
							.displayName(userRequest.getFirstname_EN() + " " + userRequest.getLastname_EN())
							.organization_name_EN(userRequest.getOrganization_name_EN())
							.role(Role.valueOf(userRequest.getItaccounttype_id()))
							.build();

			user = userRepository.save(user);

			if (userRequest.getItaccounttype_id().equals("StdAcc")) {
				Student student = Student.builder()
										 .studentId(Long.valueOf(userRequest.getStudent_id()))
										 .user(user)
										 .build();
				studentRepository.save(student);
			} else if (userRequest.getItaccounttype_id().equals("ProfAcc")) {
				Professor professor = Professor.builder()
											   .professorId(Long.valueOf(userRequest.getStudent_id()))
											   .user(user)
											   .build();
				professorRepository.save(professor);
			}

			return user;
		}

	}
