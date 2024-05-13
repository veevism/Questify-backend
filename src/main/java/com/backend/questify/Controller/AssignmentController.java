package com.backend.questify.Controller;

import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Classroom;
import com.backend.questify.Service.AssignmentService;
import com.backend.questify.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;

	@DeleteMapping("/{assignmentId}")
	public ResponseEntity<String> deleteAssignment(@PathVariable Long assignmentId) {
		return ResponseEntity.ok("Assignment deleted successfully.");
	}

	@PutMapping("/{assignmentId}")
	public ResponseEntity<String> updateAssignment(@PathVariable Long assignmentId) {
		return ResponseEntity.ok("Assignment updated successfully.");
	}

	@GetMapping("/{assignmentId}")
	public ResponseEntity<String> getAssignment(@PathVariable Long assignmentId) {
		return ResponseEntity.ok("Assignment geted successfully.");
	}

	@PostMapping("/{classroomId}")
	public ResponseEntity<String> createAssignment(@RequestBody Classroom classroomId) {
		return ResponseEntity.ok("Assignment created successfully.");
	}

}
