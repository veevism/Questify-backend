package com.backend.questify.Repository;

import com.backend.questify.Entity.Report;
import com.backend.questify.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {
    Optional<Report> findBySubmission_SubmissionId(UUID submissionId);

    Optional<Report> findBySubmission(Submission submission);

    List<Report> findAllBySubmission_Question_Laboratory_LaboratoryId(UUID laboratoryId);
}