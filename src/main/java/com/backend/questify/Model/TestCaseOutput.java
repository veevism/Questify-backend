package com.backend.questify.Model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCaseOutput {
	private String name;
	@Column(columnDefinition = "TEXT")
	private String value;
}
