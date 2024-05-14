package com.backend.questify.Controller;

import com.backend.questify.DTO.AssignmentDto;
import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.Entity.Assignment;
import com.backend.questify.Entity.Classroom;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.AssignmentService;
import com.backend.questify.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;

	@DeleteMapping("")
	public ResponseEntity<String> deleteAssignment(@RequestParam UUID assignmentId) {
		return ResponseEntity.ok("Assignment deleted successfully.");
	}

	@PutMapping("")
	public ResponseEntity<String> updateAssignment(@RequestParam UUID assignmentId) {
		return ResponseEntity.ok("Assignment updated successfully.");
	}

	@GetMapping("")
	public ResponseEntity<ApiResponse<AssignmentDto>> getAssignment(@RequestParam UUID assignmentId) {

		AssignmentDto assignment = assignmentService.getAssignment(assignmentId);

		ApiResponse<AssignmentDto> response = ApiResponse.success(assignment, HttpStatus.OK, "Get Assignment Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);

	}

	@GetMapping("/classroom")
	public ResponseEntity<ApiResponse<List<AssignmentDto>>> getAssignments(@RequestParam UUID classroomId) {

		List<AssignmentDto> result = assignmentService.getAssignments(classroomId);

		ApiResponse<List<AssignmentDto>> response = ApiResponse.success(result, HttpStatus.OK, "Get Assignment Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("")
	public ResponseEntity<ApiResponse<AssignmentDto>> createAssignment(@RequestBody Assignment assignment,
																	   @RequestParam UUID classroomId) {
		AssignmentDto result = assignmentService.createAssignment(assignment, classroomId);

		ApiResponse<AssignmentDto> response = ApiResponse.success(result, HttpStatus.CREATED, "Create Assignment Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);	}

}
