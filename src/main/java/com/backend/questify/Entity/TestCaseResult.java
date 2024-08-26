package com.backend.questify.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
    @Builder
    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "test_case_results")
    public class TestCaseResult {

        @Id
        @GeneratedValue(generator = "UUID")
        @Column(updatable = false, nullable = false)
        private UUID testCaseResultId;

        @ManyToOne
        @JoinColumn(name = "submission_id", nullable = false)
        private Submission submission;

        @ManyToOne
        @JoinColumn(name = "test_case_id", nullable = false)
        private TestCase testCase;

        @Column(columnDefinition = "TEXT")
        private String actualOutput;

        private String result;
    }
