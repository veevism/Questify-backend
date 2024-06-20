package com.backend.questify.Service;

import com.backend.questify.Entity.Professor;
import com.backend.questify.Repository.ProfessorRepository;
import com.backend.questify.Repository.UserRepository;
import com.backend.questify.Util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

	@Autowired
	private ProfessorRepository professorRepository;

	public void getAllProfessors () {
		System.out.println(DtoMapper.INSTANCE.professorToProfessorDto(professorRepository.findAll()) );
		System.out.println("Hello This is Professor Service");
	}

	public void createProfessor (Professor professor) {
		System.out.println(professor);
		System.out.println(DtoMapper.INSTANCE.professorToProfessorDto(professorRepository.findAll()) );
		System.out.println("Hello This is Professor Service");
	}
}
