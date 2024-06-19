package com.backend.questify.Repository;

import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
	Optional<Submission> findByLaboratoryAndStudent(Laboratory laboratory, Student student);

	Optional<Submission> findByLaboratoryAndProfessor (Laboratory laboratory, Professor professor);

}