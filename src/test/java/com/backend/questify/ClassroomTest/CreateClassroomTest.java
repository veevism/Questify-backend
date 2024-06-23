package com.backend.questify.ClassroomTest;

import com.backend.questify.Controller.ClassroomController;
import com.backend.questify.DTO.ClassroomDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@WebMvcTest(controllers = ClassroomController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CreateClassroomTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testCreateClassroom_Success() throws Exception {
		ClassroomDto classroomDto = new ClassroomDto();
		classroomDto.setTitle("Test Classroom");
		classroomDto.setDescription("A test classroom");

		mockMvc.perform(MockMvcRequestBuilders.post("/classroom/")
											  .contentType(MediaType.APPLICATION_JSON)
											  .content("{ \"title\": \"Test Classroom\", \"description\": \"A test classroom\" }")
											  .header("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NDIxMTgwMjQiLCJyb2xlIjoiUHJvZkFjYyIsImlhdCI6MTcxODc0NzI3NCwiZXhwIjoxNzE5NjI3Mjc0fQ.qhhAg90hX2gMCrQi011FKMtQVwuyNPfjVHxU1cnWILw"))
			   .andExpect(status().isCreated());
//			   .andExpect(jsonPath("$.data.title").value("Test Classroom"))
//			   .andExpect(jsonPath("$.message").value("Create Classroom Successfully"));

		// Verify the classroom is saved in the database
//		Classroom savedClassroom = classroomRepository.findAll().get(0);
//		assert savedClassroom.getTitle().equals("Test Classroom");
//		assert savedClassroom.getDescription().equals("A test classroom");
	}
}
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
