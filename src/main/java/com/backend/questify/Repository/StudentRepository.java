package com.backend.questify.Repository;

import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByUser_UserId(Long userId);

	Student findByStudentId(Long l);

	@Query("SELECT s FROM Student s JOIN s.classrooms c JOIN Assignment a ON a.classroom = c WHERE a = :assignment")
	List<Student> findStudentsByAssignment(@Param("assignment") Assignment assignment);

}

