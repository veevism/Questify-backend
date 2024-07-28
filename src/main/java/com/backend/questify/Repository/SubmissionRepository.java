package com.backend.questify.Repository;

import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
	Optional<Submission> findByLaboratoryAndStudent(Question question, Student student);

	Optional<Submission> findByLaboratoryAndProfessor (Question question, Professor professor);

}