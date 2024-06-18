//package com.backend.questify;
//
//import com.backend.questify.Controller.ClassroomController;
//import com.backend.questify.DTO.ClassroomDto;
//import com.backend.questify.Entity.Classroom;
//import com.backend.questify.Model.ApiResponse;
//import com.backend.questify.Service.ClassroomService;
//import com.backend.questify.Service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//class CreateClassroomTest {
//	@Mock
//	private ClassroomService classroomService;
//
//	@Mock
//	private UserService userService;
//
//	@InjectMocks
//	private ClassroomController classroomController;
//
//	@BeforeEach
//	void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//
//	@Test
//	void createClassroom_Success() {
//		ClassroomDto classroomDto = new ClassroomDto();
//		classroomDto.setTitle("701-953221-Advance Java Programming");
//		classroomDto.setDescription("This Class is going to teach you about java programming");
//
//		ClassroomDto createdClassroomDto = new ClassroomDto();
//		createdClassroomDto.setClassroomId(UUID.randomUUID());
//		createdClassroomDto.setTitle(classroomDto.getTitle());
//		createdClassroomDto.setDescription("Intro to Java");
//
//		when(classroomService.createClassroom(any(ClassroomDto.class))).thenReturn(createdClassroomDto);
//
//		ResponseEntity<ApiResponse<ClassroomDto>> response = classroomController.createClassroom(classroomDto);
//
//		assertEquals(HttpStatus.CREATED, response.getStatusCode());
//		assertEquals("Create Classroom Successfully", response.getBody().getMessage());
//		assertEquals(createdClassroomDto.getTitle(), response.getBody().getData().getTitle());
//	}
//
//	@Test
//	void createClassroom_DuplicateTitle() {
//		ClassroomDto classroomDto = new ClassroomDto();
//		classroomDto.setTitle("701-953221-Advance Java Programming");
//		classroomDto.setDescription("In this Classroom we will learning about advanced java programming");
//
//		Classroom existingClassroom = new Classroom();
//		existingClassroom.setTitle(classroomDto.getTitle());
//
//		when(classroomRepository.findByTitle(classroomDto.getTitle())).thenReturn(Optional.of(existingClassroom));
//
//		try {
//			classroomService.createClassroom(classroomDto);
//		} catch (IllegalArgumentException e) {
//			assertEquals("Classroom title cannot be duplicated.", e.getMessage());
//		}
//	}
//}
