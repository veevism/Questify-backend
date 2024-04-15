package com.backend.questify.Repository;

import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

