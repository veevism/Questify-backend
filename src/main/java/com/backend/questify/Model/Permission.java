package com.backend.questify.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Permission {
	STUDENT_READ("student:read"),
	STUDENT_WRITE("student:write"),
	PROFESSOR_READ("professor:read"),
	PROFESSOR_WRITE("professor:write");

	private final String permission;

//	public String getPermission() {
//		return permission;
//	}
}