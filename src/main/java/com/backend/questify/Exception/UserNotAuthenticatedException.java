package com.backend.questify.Exception;

public class UserNotAuthenticatedException extends RuntimeException {
	public UserNotAuthenticatedException(String message) {
		super(message);
	}
}