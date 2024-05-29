package com.backend.questify.Repository;

import com.backend.questify.Entity.Classroom;
import com.backend.questify.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {
	boolean existsByTitleAndProfessor(String title, Professor professor);

	boolean existsByTitleAndProfessorAndClassroomIdNot(String title, Professor professor, UUID classroomId);

	List<Classroom> findByProfessor_ProfessorId(Long currentUserId);

	List<Classroom> findByStudents_StudentId(Long currentUserId);
}
