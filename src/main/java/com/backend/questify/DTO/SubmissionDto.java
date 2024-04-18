package com.backend.questify.DTO;

import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Student;
import com.backend.questify.Util.HashMapConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {

	private Long submissionId;

	private Laboratory laboratory;

	private Long studentId;

	private Map<String, String> codeSnippets = new HashMap<>();

	private LocalDateTime submissionTime;

	private LocalDateTime startTime;

	private LocalDateTime endTime;
}
