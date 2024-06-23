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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

	@Autowired
	AssignmentService assignmentService;

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteAssignment(@RequestParam UUID assignmentId) {
		assignmentService.deleteAssignment(assignmentId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Assignment Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}


	@PostMapping("/assign-laboratory")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> assignLabToStudent(@RequestParam UUID assignmentId, @RequestParam UUID laboratoryId, @RequestParam Long studentId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.assignLabToStudent(assignmentId, laboratoryId, studentId), HttpStatus.OK, "Assign Laboratory Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/assign-laboratory")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> unAssignedLabToStudent(@RequestParam UUID assignmentId, @RequestParam Long studentId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.unAssignedLabToStudent(assignmentId, studentId), HttpStatus.OK, "Unassigned Laboratory Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/assign-laboratory/all")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> unAssignedLabToStudents(@RequestParam UUID assignmentId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.unAssignedLabToStudents(assignmentId), HttpStatus.OK, "Unassigned Laboratory To Students Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/assign-laboratory-random")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> randomAssignLabs(
			@RequestParam UUID assignmentId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.randomAssignLabs(assignmentId), HttpStatus.OK, "Assign Laboratory Randomly Successfully");
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> updateAssignment(@RequestParam UUID assignmentId,@RequestBody AssignmentDto assignmentDto) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.updateAssignment(assignmentId, assignmentDto), HttpStatus.OK, "Update Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> getAssignment(@RequestParam UUID assignmentId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.getAssignment(assignmentId), HttpStatus.OK, "Get Assignment Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	@GetMapping("/classroom")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<List<AssignmentDto>>> getAssignments(@RequestParam UUID classroomId) {
		ApiResponse<List<AssignmentDto>> response = ApiResponse.success(assignmentService.getAssignments(classroomId), HttpStatus.OK, "Get Assignments Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> createAssignment(@RequestBody Assignment assignment,
																	   @RequestParam UUID classroomId) {
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentService.createAssignment(assignment, classroomId), HttpStatus.CREATED, "Create Assignment Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);	}

}
