package com.backend.questify.Controller;

import com.backend.questify.DTO.ClassroomDto;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

	@Autowired
	ClassroomService classroomService;

	@DeleteMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<Void>> deleteClassroom(@RequestParam UUID classroomId) {
		classroomService.deleteClassroom(classroomId);
		ApiResponse<Void> response = ApiResponse.success(null, HttpStatus.OK, "Delete Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping("")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> updateClassroom(@RequestParam UUID classroomId, @RequestBody ClassroomDto classroomDto) {
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomService.updateClassroom(classroomId, classroomDto), HttpStatus.OK, "Update Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> getClassroom(@RequestParam UUID classroomId) {
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomService.getClassroom(classroomId), HttpStatus.OK, "Get Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<List<ClassroomDto>>> getClassrooms() {
		ApiResponse<List<ClassroomDto>> response = ApiResponse.success(classroomService.getClassrooms(), HttpStatus.OK, "Get Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> createClassroom(@RequestBody ClassroomDto classroomDto) {
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomService.createClassroom(classroomDto), HttpStatus.CREATED, "Create Classroom Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/join")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> joinClassroom(@RequestParam String invitationCode) {
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomService.joinClassroom(invitationCode), HttpStatus.OK, "Join Classroom Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/remove")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> removeFromClassroom(@RequestParam UUID classroomId, @RequestParam Long studentId) {
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomService.removeFromClassroom(classroomId, studentId), HttpStatus.OK, "Remove Student With Id : " + studentId + " Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}



}
