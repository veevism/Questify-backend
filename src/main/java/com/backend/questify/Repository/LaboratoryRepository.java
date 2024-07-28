package com.backend.questify.Repository;

import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LaboratoryRepository extends JpaRepository<Laboratory, UUID> {
//
//	List<Classroom> findByProfessor_ProfessorId(Long currentUserId);
//
//	List<Classroom> findByStudents_StudentId(Long currentUserId);
//
//
//	Optional<Classroom> findByInvitationCode(String invitationCode);
    Optional<Laboratory> findByInvitationCode(String invitationCode);

    Optional<Laboratory> findByTitleAndProfessor(String title, Professor professor);


    List<Laboratory> findAllByProfessor_ProfessorId(Long userId);

//    List<Laboratory> findAll(Long userId);
}
