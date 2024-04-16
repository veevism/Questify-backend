package com.backend.questify.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private String firstName;

	private String lastName;

	private String displayName;

	private String userName;

	private String email;
}
