package com.backend.questify.Exception;

import java.util.List;

public class ListIsNotEmptyException {
	public static <T> List<T> requireNotEmpty(List<T> items, String className) throws ResourceNotFoundException {
		if (items.isEmpty()) {
			throw new ResourceNotFoundException(className + " not found");
		}
		return items;
	}
}
