package com.backend.questify;

import com.backend.questify.DTO.User.UserRequestDto;
import com.backend.questify.Entity.Laboratory;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.User;
import com.backend.questify.DTO.LaboratoryDto;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.Status;
import com.backend.questify.Repository.LaboratoryRepository;
import com.backend.questify.Repository.UserRepository;
import com.backend.questify.Service.LaboratoryService;
import com.backend.questify.Service.UserService;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import com.backend.questify.Model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LaboratoryServiceTest {

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityHelper entityHelper;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private LaboratoryService laboratoryService;

    @InjectMocks
    private UserService userService;

    private Laboratory laboratory;
    private Professor professor;
    private Student student1;
    private Student student2;
    private LaboratoryDto laboratoryDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor = new Professor();
        professor.setProfessorId(542118024L);

        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setStudent_id("542118024");
        userRequestDto.setFirstname_EN("PATHATHAI");
        userRequestDto.setLastname_EN("NALUMPOON");
        userRequestDto.setCmuitaccount("pathathai.n@cmu.ac.th");
        userRequestDto.setCmuitaccount_name("PATHATHAI NALUMPOON");
        userRequestDto.setOrganization_name_EN("College of Arts, Media and Technology");
        userRequestDto.setItaccounttype_id("ProfAcc");

        User user = createUser(userRequestDto);
        professor = user.getProfessor();

        laboratory = Laboratory.builder()
                .laboratoryId(UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b"))
                .title("Learn Calculation Sequence In Programming")
                .description("In this laboratory, we will provide you with a question and need you to do basic calculation on each shape such as Triangle and Square")
                .maxScore(100)
                .status(Status.DRAFT)
                .durationTime(600)
                .professor(professor)
                .build();

        laboratoryDto = new LaboratoryDto();
        laboratoryDto.setLaboratoryId(laboratory.getLaboratoryId()); // Set the laboratoryId to match the actual value
        laboratoryDto.setProfessor(DtoMapper.INSTANCE.professorToProfessorDto(professor)); // Set the professor to match the actual value
        laboratoryDto.setTitle(laboratory.getTitle());
        laboratoryDto.setDescription(laboratory.getDescription());
        laboratoryDto.setMaxScore(laboratory.getMaxScore());
        laboratoryDto.setStatus(laboratory.getStatus());
        laboratoryDto.setDurationTime(laboratory.getDurationTime());

        student1 = new Student();
        student1.setStudentId(642115031L);

        User studentUser1 = User.builder()
                .userId(642115031L)
                .userName("phiriyakorn_m")
                .firstName_EN("PHIRIYAKORN")
                .lastName_EN("MANEEKONGRIT")
                .displayName("PHIRIYAKORN MANEEKONGRIT")
                .email("phiriyakorn_m@cmu.ac.th")
                .organization_name_EN("College of Arts, Media and Technology")
                .role(Role.StdAcc)
                .student(student1)
                .build();

        student1.setUser(studentUser1);

        student2 = new Student();
        student2.setStudentId(642115045L);

        User studentUser2 = User.builder()
                .userId(642115045L)
                .userName("sorawee_sri")
                .firstName_EN("SORAWEE")
                .lastName_EN("SRIPHAKDEEPHONGDEJ")
                .displayName("SORAWEE SRIPHAKDEEPHONGDEJ")
                .email("sorawee_sri@cmu.ac.th")
                .organization_name_EN("College of Arts, Media and Technology")
                .role(Role.StdAcc)
                .student(student2)
                .build();

        student2.setUser(studentUser2);
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


    @Nested
    @DisplayName("Tests for createLaboratory method")
    class CreateLaboratoryTests {

        @Test
        @DisplayName("Should create laboratory successfully")
        void createLaboratory_ShouldCreateLaboratorySuccessfully() {
            when(entityHelper.findProfessorById(any(Long.class))).thenReturn(professor);
            when(laboratoryRepository.existsByTitleAndProfessor(anyString(), any(Professor.class))).thenReturn(false);
            when(laboratoryRepository.save(any(Laboratory.class))).thenAnswer(invocation -> {
                Laboratory savedLab = invocation.getArgument(0);
                savedLab.setLaboratoryId(UUID.randomUUID());
                savedLab.setInvitationCode("6a7426ba");
                return savedLab;
            });
            when(dtoMapper.laboratoryToLaboratoryDto(any(Laboratory.class))).thenReturn(laboratoryDto);

            LaboratoryDto result = laboratoryService.createLaboratory(laboratory);

            assertNotNull(result);
            assertEquals(laboratoryDto.getTitle(), result.getTitle());
            assertEquals(laboratoryDto.getDescription(), result.getDescription());
            assertEquals(laboratoryDto.getMaxScore(), result.getMaxScore());
            assertEquals(laboratoryDto.getStatus(), result.getStatus());
            assertEquals(laboratoryDto.getDurationTime(), result.getDurationTime());
            verify(laboratoryRepository, times(1)).save(any(Laboratory.class));

            System.out.println("Create Laboratory Test Passed. Result: " + result);
        }

        @Test
        @DisplayName("Should throw exception when title is duplicated")
        void createLaboratory_ShouldThrowExceptionWhenTitleIsDuplicated() {
            when(entityHelper.findProfessorById(any(Long.class))).thenReturn(professor);
            when(laboratoryRepository.existsByTitleAndProfessor(anyString(), any(Professor.class))).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
                laboratoryService.createLaboratory(laboratory);
            });

            assertEquals("Laboratory title cannot be duplicated.", exception.getMessage());
            verify(laboratoryRepository, times(0)).save(any(Laboratory.class));

            System.out.println("Create Laboratory Test Failed as Expected. Exception: " + exception.getMessage());
        }
    }


    @Nested
    @DisplayName("Tests for updateLaboratory method")
    class UpdateLaboratoryTests {

        @Test
        @DisplayName("Should update laboratory successfully")
        void updateLaboratory_ShouldUpdateLaboratorySuccessfully() {
            UUID laboratoryId = UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b");

            when(entityHelper.findLaboratoryById(any(UUID.class))).thenReturn(laboratory);
            when(entityHelper.findProfessorById(anyLong())).thenReturn(professor);
            when(laboratoryRepository.save(any(Laboratory.class))).thenReturn(laboratory);
            when(dtoMapper.laboratoryToLaboratoryDto(any(Laboratory.class))).thenReturn(laboratoryDto);

            LaboratoryDto result = laboratoryService.updateLaboratory(laboratoryId, laboratory);

            assertNotNull(result);
            assertEquals("Learn Calculation Sequence In Programming", result.getTitle());
            assertEquals("In this laboratory, we will provide you with a question and need you to do basic calculation on each shape such as Triangle and Square", result.getDescription());
            assertEquals(100, result.getMaxScore());
            assertEquals(Status.DRAFT, result.getStatus());
            assertEquals(600, result.getDurationTime());

            verify(laboratoryRepository, times(1)).save(any(Laboratory.class));

            System.out.println("Update Laboratory Test Passed. Result: " + result);
        }
    }

//    @Nested
//    @DisplayName("Tests for deleteLaboratory method")
//    class DeleteLaboratoryTests {
//
//        @Test
//        @DisplayName("Should delete laboratory successfully")
//        void deleteLaboratory_ShouldDeleteLaboratorySuccessfully() {
//            UUID laboratoryId = UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b");
//
//            when(entityHelper.findLaboratoryById(laboratoryId)).thenReturn(laboratory);
//
//            laboratoryService.deleteLaboratory(laboratoryId);
//
//            verify(laboratoryRepository, times(1)).deleteById(laboratoryId);
//
//            System.out.println("Delete Laboratory Test Passed. Laboratory ID: " + laboratoryId);
//        }
//    }

    @Nested
    @DisplayName("Tests for getLaboratories method")
    class GetLaboratoriesTests {

        @Test
        @DisplayName("Should return list of laboratories successfully for professor")
        void getLaboratories_ShouldReturnLaboratoriesSuccessfully_ForProfessor() {
            when(entityHelper.getCurrentUser()).thenReturn(professor.getUser());
            when(entityHelper.findLaboratoriesByProfessor(any(User.class))).thenReturn(List.of(laboratory));
            when(dtoMapper.laboratoryToLaboratoryDto(any(Laboratory.class))).thenReturn(laboratoryDto);

            List<LaboratoryDto> result = laboratoryService.getLaboratories();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(laboratoryDto, result.get(0));

            System.out.println("Get Laboratories Test Passed for Professor. Result: " + result);
        }

        @Test
        @DisplayName("Should return list of laboratories successfully for student")
        void getLaboratories_ShouldReturnLaboratoriesSuccessfully_ForStudent() {
            when(entityHelper.getCurrentUser()).thenReturn(student1.getUser());
            when(entityHelper.findLaboratoriesByStudent(any(User.class))).thenReturn(List.of(laboratory));
            when(dtoMapper.laboratoryToLaboratoryDto(any(Laboratory.class))).thenReturn(laboratoryDto);

            List<LaboratoryDto> result = laboratoryService.getLaboratories();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(laboratoryDto, result.get(0));

            System.out.println("Get Laboratories Test Passed for Student. Result: " + result);
        }

        @Test
        @DisplayName("Should throw exception when no laboratories found")
        void getLaboratories_ShouldThrowExceptionWhenNoLaboratoriesFound() {
            when(entityHelper.getCurrentUser()).thenReturn(professor.getUser());
            when(entityHelper.findLaboratoriesByProfessor(any(User.class))).thenThrow(new ResourceNotFoundException("No laboratories found!"));

            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                laboratoryService.getLaboratories();
            });

            assertEquals("No laboratories found!", exception.getMessage());

            System.out.println("Get Laboratories Test Failed as Expected. Exception: " + exception.getMessage());
        }
    }
}
