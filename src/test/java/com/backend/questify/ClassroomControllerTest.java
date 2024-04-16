package com.backend.questify;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

//@SpringBootTest
//@AutoConfigureMockMvc
//public class ClassroomControllerTests {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Test
//	public void getClassroom_NotFound() throws Exception {
//		mockMvc.perform(get("/classrooms/999"))
//			   .andExpect(status().isNotFound())
//			   .andExpect(content().string(containsString("Classroom not found with id: 999")));
//	}
//}