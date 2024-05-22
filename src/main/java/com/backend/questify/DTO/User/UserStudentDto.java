package com.backend.questify.DTO.User;

import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.DTO.StudentDto;
import com.backend.questify.Model.Role;
import lombok.*;

@Builder
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserStudentDto {
	private Long userId;

	private String firstName_EN;

	private String lastName_EN;

	private String displayName;

	private String userName;

	private String email;

	private String organization_name_EN;

	private StudentDto student;

	private Role role;
}
