package com.backend.questify.Repository;

import com.backend.questify.Entity.Submission;
import com.backend.questify.Entity.TestCaseResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestCaseResultRepository extends JpaRepository<TestCaseResult, UUID> {

    void deleteAllBySubmission (Submission submission);
}
