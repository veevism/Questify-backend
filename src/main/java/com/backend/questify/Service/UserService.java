package com.backend.questify.Service;

import com.backend.questify.Entity.*;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Repository.StudentRepository;
import com.backend.questify.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


	@Service
	public class UserService {

		@Autowired
		private UserRepository userRepository;

		public User createUser(User user) {
			return userRepository.save(user);
		}

		public Optional<User> getUserById(Long userId) {
			return userRepository.findById(userId);
		}

		public Optional<User> getUserByUserName(String userName) {
			return userRepository.findByUserName(userName);
		}

		public List<User> getAllUsers() {
			return userRepository.findAll();
		}

		public User updateUser(Long userId, User userDetails) {
			User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with id " + userId));
			user.setFirstName(userDetails.getFirstName());
			user.setLastName(userDetails.getLastName());
			user.setDisplayName(userDetails.getDisplayName());
			user.setPassword(userDetails.getPassword());
			user.setImage(userDetails.getImage());
			user.setEmail(userDetails.getEmail());
			user.setRole(userDetails.getRole());
			return userRepository.save(user);
		}

		public void deleteUser(Long userId) {
			userRepository.deleteById(userId);
		}
	}
