package com.backend.questify;

import com.backend.questify.DTO.User.UserDto;
import com.backend.questify.DTO.User.UserRequestDto;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.User;
import com.backend.questify.Model.Role;
import com.backend.questify.Repository.UserRepository;
import com.backend.questify.Security.JwtTokenProvider;
import com.backend.questify.Service.UserService;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private EntityHelper entityHelper;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private UserService userService;

    private User studentUser;
    private User professorUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UserRequestDto studentRequest = new UserRequestDto();
        studentRequest.setStudent_id("642115045");
        studentRequest.setFirstname_EN("SORAWEE");
        studentRequest.setLastname_EN("SRIPHAKDEEPHONGDEJ");
        studentRequest.setCmuitaccount("sorawee_sri@cmu.ac.th");
        studentRequest.setCmuitaccount_name("sorawee_sri");
        studentRequest.setOrganization_name_EN("College of Arts, Media and Technology");
        studentRequest.setItaccounttype_id("StdAcc");

        studentUser = createUser(studentRequest);

        UserRequestDto professorRequest = new UserRequestDto();
        professorRequest.setStudent_id("542118024");
        professorRequest.setFirstname_EN("PATHATHAI");
        professorRequest.setLastname_EN("NALUMPOON");
        professorRequest.setCmuitaccount("pathathai.n@cmu.ac.th");
        professorRequest.setCmuitaccount_name("PATHATHAI NALUMPOON");
        professorRequest.setOrganization_name_EN("College of Arts, Media and Technology");
        professorRequest.setItaccounttype_id("ProfAcc");

        professorUser = createUser(professorRequest);
    }

    private User createUser(UserRequestDto userRequest) {
        Long userId = Long.valueOf(userRequest.getStudent_id());

        User user = User.builder()
                .userId(userId)
                .userName(userRequest.getCmuitaccount_name())
                .email(userRequest.getCmuitaccount())
                .firstName_EN(userRequest.getFirstname_EN())
                .lastName_EN(userRequest.getLastname_EN())
                .displayName(userRequest.getFirstname_EN() + " " + userRequest.getLastname_EN())
                .organization_name_EN(userRequest.getOrganization_name_EN())
                .role(Role.valueOf(userRequest.getItaccounttype_id()))
                .build();

        if (user.getRole() == Role.StdAcc) {
            Student student = Student.builder()
                    .studentId(userId)
                    .user(user)
                    .build();
            user.setStudent(student);
        } else if (user.getRole() == Role.ProfAcc) {
            Professor professor = Professor.builder()
                    .professorId(userId)
                    .user(user)
                    .build();
            user.setProfessor(professor);
        }
        return user;
    }

    @Test
    @DisplayName("Should get profile for student successfully")
    void getProfile_ShouldReturnStudentProfile() {
        when(entityHelper.getCurrentUserId()).thenReturn(studentUser.getUserId());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(studentUser));
        when(dtoMapper.userToUserDto(any(User.class))).thenReturn(DtoMapper.INSTANCE.userToUserDto(studentUser));

        UserDto result = userService.getProfile();

        assertNotNull(result);
        assertEquals(studentUser.getUserId(), result.getUserId());
        assertEquals("StdAcc", result.getRole().name());
        assertNotNull(result.getStudent());
        assertNull(result.getProfessor());

        System.out.println("Get Profile Test Passed for Student. Result: " + result);
    }

    @Test
    @DisplayName("Should get profile for professor successfully")
    void getProfile_ShouldReturnProfessorProfile() {
        when(entityHelper.getCurrentUserId()).thenReturn(professorUser.getUserId());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(professorUser));
        when(dtoMapper.userToUserDto(any(User.class))).thenReturn(DtoMapper.INSTANCE.userToUserDto(professorUser));

        UserDto result = userService.getProfile();

        assertNotNull(result);
        assertEquals(professorUser.getUserId(), result.getUserId());
        assertEquals("ProfAcc", result.getRole().name());
        assertNotNull(result.getProfessor());
        assertNull(result.getStudent());

        System.out.println("Get Profile Test Passed for Professor. Result: " + result);
    }
}
