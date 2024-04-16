package com.backend.questify.Repository;

import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Optional<Student> findByUser_UserId(Long userId);
}

