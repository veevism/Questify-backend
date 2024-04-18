package com.backend.questify.DTO;

import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long userId;

	private String firstName;

	private String lastName;

	private String displayName;

	private String userName;

	private String email;

	private StudentDto student;

	private ProfessorDto professor;



	//! Todo fix mapstruct by deleted them first, delete lazy loading <- print original user first to see if data come propoerly or not
}
