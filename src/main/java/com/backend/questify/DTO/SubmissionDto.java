package com.backend.questify.DTO;

import com.backend.questify.Model.SubmissionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionDto {

	private UUID submissionId;
	private Long studentId;
	private Map<String, String> codeSnippets = new HashMap<>();
	private LocalDateTime submissionTime;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private SubmissionStatus status;
	private List<TestCaseResultDto> testCaseResults;
}
