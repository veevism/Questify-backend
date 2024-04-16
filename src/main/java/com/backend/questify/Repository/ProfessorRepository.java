package com.backend.questify.Repository;

import com.backend.questify.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	Optional<Professor> findByUser_UserId(Long userId);
}