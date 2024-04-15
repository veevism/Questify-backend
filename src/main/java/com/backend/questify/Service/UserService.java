package com.backend.questify.Service;

import com.backend.questify.Entity.*;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ProfessorRepository professorRepository;

	@Transactional
	public User registerUser(User user, String role) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalStateException("Email already in use.");
		}

		User createdUser = new User();
		createdUser.setUserName(user.getUserName());
		createdUser.setPassword(user.getPassword());  // Consider encrypting the password
		createdUser.setEmail(user.getEmail());
		createdUser.setRole(Role.valueOf(role.toUpperCase()));

		createdUser = userRepository.save(createdUser);

		if (role.equalsIgnoreCase("student")) {
			Student student = new Student();
			student.setUser(createdUser);
			studentRepository.save(student);
		} else if (role.equalsIgnoreCase("professor")) {
			Professor professor = new Professor();
			professor.setUser(createdUser);
			professorRepository.save(professor);
		}

		return createdUser;
	}
}