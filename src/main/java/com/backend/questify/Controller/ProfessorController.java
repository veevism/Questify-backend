package com.backend.questify.Controller;

import com.backend.questify.Entity.Professor;
import com.backend.questify.Service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {
	@Autowired
	private ProfessorService professorService;

	@GetMapping("/")
	public ResponseEntity<String> getAllProfessors () {
		professorService.getAllProfessors();
		return ResponseEntity.ok("Hi");
	}

	@PostMapping("/")
	public ResponseEntity<String> createProfessor (@RequestBody Professor professor) {
		professorService.createProfessor(professor);
		return ResponseEntity.ok("Hi");
	}

//	@PostMapping("/:professorId")
//	public ResponseEntity<String> getProfessorById (@RequestParam Long id) {
//
//	}

//	@PutMapping("/:professorId")
//	public ResponseEntity<String> updateProfessorById (@RequestParam Long id) {
//
//			return ResponseEntity.ok("Hi");
//	}



}
