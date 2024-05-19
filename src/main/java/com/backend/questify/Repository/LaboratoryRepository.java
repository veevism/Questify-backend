package com.backend.questify.Repository;


import com.backend.questify.Entity.Laboratory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface LaboratoryRepository extends JpaRepository<Laboratory, UUID> {

	List<Laboratory> findAllByAssignment_AssignmentId(UUID assignmentId);
}
