package com.backend.questify.Controller;

import com.backend.questify.DTO.StudentDto;
import com.backend.questify.DTO.StudentWithLaboratory;
import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@GetMapping("")
	public ResponseEntity<ApiResponse<List<StudentWithLaboratory>>> getStudents (@RequestParam(required = false) UUID classroomId,
																				 @RequestParam(required = false) UUID assignmentId,
																				 @RequestParam(required = false) String name) {
		ApiResponse<List<StudentWithLaboratory>> response = ApiResponse.success(studentService.getStudents(classroomId,assignmentId,name), HttpStatus.OK, "Get Students Successfully" );
		return ResponseEntity.status(response.getStatus()).body(response);

	}
}
