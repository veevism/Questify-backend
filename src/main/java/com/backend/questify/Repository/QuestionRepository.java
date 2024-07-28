package com.backend.questify.Repository;


import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

//	List<Question> findAllByAssignment_AssignmentId(UUID assignmentId);
//
//	List<Question> findAllByAssignment (Laboratory assignment);
//
//	List<Question> findAllByLaboratoryId (UUID laboratoryId);

	List<Question> findAllByLaboratory_LaboratoryId (UUID laboratoryId);

	List<Question> findAllByLaboratory(Laboratory laboratory);



}
