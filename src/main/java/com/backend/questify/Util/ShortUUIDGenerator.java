package com.backend.questify.Util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;


public class ShortUUIDGenerator {
	public static String generateShortUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").substring(0, 8);
	}
}
