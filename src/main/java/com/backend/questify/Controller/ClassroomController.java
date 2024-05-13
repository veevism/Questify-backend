package com.backend.questify.Controller;

import com.backend.questify.Entity.Classroom;
import com.backend.questify.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

	@Autowired
	ClassroomService classroomService;

	@DeleteMapping("/{classroomId}")
	public ResponseEntity<String> deleteClassroom(@PathVariable Long classroomId) {
		return ResponseEntity.ok("Classroom deleted successfully.");
	}

	@PutMapping("/{classroomId}")
	public ResponseEntity<String> updateClassroom(@PathVariable Long classroomId) {
		return ResponseEntity.ok("Classroom updated successfully.");
	}

	@GetMapping("/{classroomId}")
	public ResponseEntity<String> getClassroom(@PathVariable Long classroomId) {
		return ResponseEntity.ok("Classroom geted successfully.");
	}

	@PostMapping("/")
	public ResponseEntity<String> createClassroom(@RequestBody Classroom classroom) {
		System.out.println(classroom);
		return ResponseEntity.ok("Classroom created successfully.");
	}


}
