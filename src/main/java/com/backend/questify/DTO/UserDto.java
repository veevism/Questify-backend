package com.backend.questify.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

	private String firstName;

	private String lastName;

	private String displayName;

	private String userName;

	private String email;
}
