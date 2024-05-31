package com.backend.questify.Repository;

import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, UUID> {

	List<Assignment> findAllByClassroom_ClassroomId(UUID classroomId);

	Optional<Assignment> findByAssignmentId(UUID assignmentId);

	Optional<Assignment> findByAssignmentIdAndProfessor(UUID assignmentId, Professor professor);

//	List<Assignment> findAllByClassroom_ClassroomIdAndAndProfessor(UUID classroomId, Professor professor);
}
