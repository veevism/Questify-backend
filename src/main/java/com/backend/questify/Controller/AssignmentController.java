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
		AssignmentDto assignmentDto = assignmentService.assignLabToStudent(assignmentId, laboratoryId, studentId);
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentDto, HttpStatus.OK, "Assign Laboratory Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/assign-laboratory-random")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> randomAssignLabs(
			@RequestParam UUID assignmentId) {
		AssignmentDto assignmentDto = assignmentService.randomAssignLabs(assignmentId);
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentDto, HttpStatus.OK, "Assign Laboratory Randomly Successfully");

		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> updateAssignment(@RequestParam UUID assignmentId,@RequestBody AssignmentDto assignmentDto) {
		AssignmentDto assignmentDto1 = assignmentService.updateAssignment(assignmentId, assignmentDto);
		ApiResponse<AssignmentDto> response = ApiResponse.success(assignmentDto1, HttpStatus.OK, "Update Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
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
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<AssignmentDto>> createAssignment(@RequestBody Assignment assignment,
																	   @RequestParam UUID classroomId) {
		AssignmentDto result = assignmentService.createAssignment(assignment, classroomId);

		ApiResponse<AssignmentDto> response = ApiResponse.success(result, HttpStatus.CREATED, "Create Assignment Successfully" );

		return ResponseEntity.status(response.getStatus()).body(response);	}

}
