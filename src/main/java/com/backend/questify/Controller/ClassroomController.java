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
		ClassroomDto classroomDto1 = classroomService.updateClassroom(classroomId, classroomDto);
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomDto1, HttpStatus.OK, "Update Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> getClassroom(@RequestParam UUID classroomId) {
		ClassroomDto classroomDto1 = classroomService.getClassroom(classroomId);
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomDto1, HttpStatus.OK, "Get Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/")
	@PreAuthorize("hasAnyRole('StdAcc', 'ProfAcc')")
	public ResponseEntity<ApiResponse<List<ClassroomDto>>> getClassrooms() {
		List<ClassroomDto> classroomDto1 = classroomService.getClassrooms();
		ApiResponse<List<ClassroomDto>> response = ApiResponse.success(classroomDto1, HttpStatus.OK, "Get Classrooms Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> createClassroom(@RequestBody ClassroomDto classroomDto) {
		ClassroomDto classroomDto1 = classroomService.createClassroom(classroomDto);
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomDto1, HttpStatus.CREATED, "Create Classroom Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/join")
	@PreAuthorize("hasRole('StdAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> joinClassroom(@RequestParam UUID classroomId) {
		ClassroomDto classroomDto = classroomService.joinClassroom(classroomId);
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomDto, HttpStatus.OK, "Join Classroom With Id : " + classroomId + " Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@DeleteMapping("/remove")
	@PreAuthorize("hasRole('ProfAcc')")
	public ResponseEntity<ApiResponse<ClassroomDto>> removeFromClassroom(@RequestParam UUID classroomId, @RequestParam Long studentId) {
		ClassroomDto classroomDto = classroomService.removeFromClassroom(classroomId, studentId);
		ApiResponse<ClassroomDto> response = ApiResponse.success(classroomDto, HttpStatus.OK, "Remove Student From Classroom With Id : " + classroomId + " Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);
	}



}
