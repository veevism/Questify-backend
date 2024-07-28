package com.backend.questify.Repository;

import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByUser_UserId(Long userId);

	Student findByStudentId(Long l);

	@Query("SELECT s FROM Student s JOIN s.classrooms c JOIN Assignment a ON a.classroom = c WHERE a = :assignment")
	List<Student> findStudentsByAssignment(@Param("assignment") Laboratory assignment);

}

