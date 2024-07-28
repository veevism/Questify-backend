package com.backend.questify.Repository;

import com.backend.questify.Entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestCaseRepository extends JpaRepository<TestCase, UUID> {

	List<TestCase> findAllByQuestion_QuestionId(UUID questionId);}
