package com.backend.questify.Entity;

import com.backend.questify.Util.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "submissions")
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submissionId;

	@ManyToOne
	@JoinColumn(name = "laboratory_id")
	private Laboratory laboratory;

	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;

	@Convert(converter = HashMapConverter.class)
	@Column(columnDefinition = "TEXT")
	private Map<String, String> codeSnippets = new HashMap<>();

	@PrePersist
	public void prePersist() {
		this.startTime = LocalDateTime.now();
		if (this.codeSnippets.isEmpty()) {
			this.codeSnippets.put("Java", "public class Main { public static void main(String[] args) { System.out.println(\"Welcome To Questify Ja\"); } }");
			this.codeSnippets.put("Python", "print('Welcome To Questify Ja')");
			this.codeSnippets.put("C", "#include <stdio.h>\nint main() { printf(\"Welcome To Questify Ja\"); return 0; }");
			this.codeSnippets.put("JavaScript", "console.log('Welcome To Questify Ja');");
		}
	}

	@Column(nullable = true)
	private LocalDateTime submissionTime;

	@Column(nullable = true)
	private LocalDateTime startTime;

	@Column(nullable = true)
	private LocalDateTime endTime;
}