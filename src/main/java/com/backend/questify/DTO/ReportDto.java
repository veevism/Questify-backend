package com.backend.questify.DTO;

import com.backend.questify.Entity.Logging;
import com.backend.questify.Model.SubmitStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {
    private Long id;
    private SubmissionDto submission;
    private List<LoggingDto> loggings;
//    private Long testCaseId;
    private Integer givenScore;
    private Integer maxScore;
    private LocalDateTime startTime;
    private LocalDateTime submitTime;
    private SubmitStatus submitStatus;
}