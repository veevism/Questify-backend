package com.backend.questify.DTO;

import com.backend.questify.Model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private UUID questionId;

    private LaboratoryDto laboratory;

    private String title;

    private String problemStatement;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
