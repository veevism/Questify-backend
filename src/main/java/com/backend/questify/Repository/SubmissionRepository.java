package com.backend.questify.Repository;

import com.backend.questify.Entity.Question;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, UUID> {

	Optional<Submission> findByQuestionAndStudent(Question question, Student student);

	Optional<Submission> findByQuestionAndProfessor (Question question, Professor professor);

	Optional<Submission> findByQuestion(Question question);
}