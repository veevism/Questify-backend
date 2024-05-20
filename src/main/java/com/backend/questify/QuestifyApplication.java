package com.backend.questify;

import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.User;
import com.backend.questify.Model.Role;
import com.backend.questify.Service.UserService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class QuestifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestifyApplication.class, args);
	}

	@Bean
	public CommandLineRunner init(UserService userService) {
		return args -> {


			User studentUser1 = User.builder()
								   .userName("student1")
								   .firstName("Student")
								   .lastName("User")
								   .displayName("Student User")
								   .password("studentpassword")
								   .email("student1@example.com")
								   .role(Role.STUDENT)
								   .build();
			userService.createUser(studentUser1);

			User professorUser = User.builder()
								   .userName("admin")
								   .firstName("Admin")
								   .lastName("User")
								   .displayName("Admin User")
								   .password("adminpassword")
								   .email("admin@example.com")
								   .role(Role.PROFESSOR)
								   .build();
			userService.createUser(professorUser);

			User studentUser2 = User.builder()
									.userName("student2")
									.firstName("Student")
									.lastName("User")
									.displayName("Student User")
									.password("studentpassword")
									.email("student2@example.com")
									.role(Role.STUDENT)
									.build();
			userService.createUser(studentUser2);

			User studentUser3 = User.builder()
									.userName("student3")
									.firstName("Student")
									.lastName("User")
									.displayName("Student User")
									.password("studentpassword")
									.email("student3@example.com")
									.role(Role.STUDENT)
									.build();
			userService.createUser(studentUser3);

			User studentUser4 = User.builder()
									.userName("student4")
									.firstName("Student")
									.lastName("User")
									.displayName("Student User")
									.password("studentpassword")
									.email("student4@example.com")
									.role(Role.STUDENT)
									.build();
			userService.createUser(studentUser4);


//			Classroom classroom1 = Classroom.builder()
//					.professor(professorUser).
//											.build();
		};
	}

}
