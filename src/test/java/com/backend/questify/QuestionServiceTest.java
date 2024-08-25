package com.backend.questify;

import com.backend.questify.DTO.QuestionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Model.Role;
import com.backend.questify.Model.Status;
import com.backend.questify.Repository.QuestionRepository;
import com.backend.questify.Service.QuestionService;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private EntityHelper entityHelper;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private QuestionService questionService;

    private Laboratory laboratory;
    private Professor professor;
    private Student student1;
    private Question question;
    private QuestionDto questionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        professor = new Professor();
        professor.setProfessorId(542118024L);

        User professorUser = User.builder()
                .userId(542118024L)
                .userName("PATHATHAI NALUMPOON")
                .firstName_EN("PATHATHAI")
                .lastName_EN("NALUMPOON")
                .displayName("PATHATHAI NALUMPOON")
                .email("pathathai.n@cmu.ac.th")
                .organization_name_EN("College of Arts, Media and Technology")
                .role(Role.ProfAcc)
                .professor(professor)
                .build();

        professor.setUser(professorUser);


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


        when(entityHelper.getCurrentUser()).thenReturn(studentUser1);

        laboratory = Laboratory.builder()
                .laboratoryId(UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b"))
                .title("Learn Calculation Sequence In Programming")
                .description("In this laboratory, we will provide you with a question and need you to do basic calculation on each shape such as Triangle and Square")
                .maxScore(100)
                .status(Status.PUBLISH)
                .durationTime(600)
                .professor(professor)
                .studentQuestion(new HashMap<>())
                .build();

        laboratory.getStudentQuestion().put(student1.getStudentId(), UUID.fromString("c2f7ae68-7f07-49c9-93a4-4f47d182f7eb"));

        question = Question.builder()
                .questionId(UUID.fromString("c2f7ae68-7f07-49c9-93a4-4f47d182f7eb"))
                .title("Version 1: Calculate the Area of a Rectangle")
                .problemStatement("You are given two positive integers, length and width, representing the dimensions of a rectangle. Your task is to implement a function calculateArea(length: int, width: int) -> int that returns the area of the rectangle.")
                .laboratory(laboratory)
                .professor(professor)
                .build();

        questionDto = new QuestionDto();
        questionDto.setQuestionId(question.getQuestionId());
        questionDto.setTitle(question.getTitle());
        questionDto.setProblemStatement(question.getProblemStatement());
    }

    @Nested
    @DisplayName("Tests for createQuestion method")
    class CreateQuestionTests {

        @Test
        @DisplayName("Should create question successfully")
        void createQuestion_ShouldCreateQuestionSuccessfully() {
            when(entityHelper.findLaboratoryById(any(UUID.class))).thenReturn(laboratory);
            when(entityHelper.findProfessorById(anyLong())).thenReturn(professor);
            when(questionRepository.save(any(Question.class))).thenAnswer(invocation -> {
                Question savedQuestion = invocation.getArgument(0);
                savedQuestion.setQuestionId(UUID.fromString("c2f7ae68-7f07-49c9-93a4-4f47d182f7eb"));
                return savedQuestion;
            });
            when(dtoMapper.questionToQuestionDto(any(Question.class))).thenReturn(questionDto);

            QuestionDto result = questionService.createQuestion(UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b"), questionDto);

            assertNotNull(result);
            assertEquals(questionDto.getTitle(), result.getTitle());
            assertEquals(questionDto.getProblemStatement(), result.getProblemStatement());
            verify(questionRepository, times(1)).save(any(Question.class));

            System.out.println("Create Question Test Passed. Result: " + result);
        }
    }

    @Nested
    @DisplayName("Tests for getQuestions method")
    class GetQuestionsTests {

        @Test
        @DisplayName("Should retrieve questions for StdAcc role")
        void getQuestions_ShouldRetrieveQuestionsForStdAcc() {
            UUID laboratoryId = UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b");

            when(entityHelper.getCurrentUser()).thenReturn(student1.getUser());

            Authentication auth = new UsernamePasswordAuthenticationToken(student1.getUser(), null, List.of(new SimpleGrantedAuthority("ROLE_StdAcc")));
            SecurityContextHolder.getContext().setAuthentication(auth);

            when(entityHelper.findLaboratoryById(laboratoryId)).thenReturn(laboratory);

            when(questionRepository.findAllByQuestionId(any(UUID.class))).thenReturn(List.of(question));

            when(dtoMapper.questionToQuestionDto(any(Question.class))).thenReturn(questionDto);

            List<QuestionDto> result = questionService.getQuestions(laboratoryId);

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(questionDto.getTitle(), result.get(0).getTitle());

            System.out.println("Get Questions Test Passed for StdAcc Role. Result: " + result);
        }

        @Test
        @DisplayName("Should retrieve questions for ProfAcc role")
        void getQuestions_ShouldRetrieveQuestionsForProfAcc() {
            UUID laboratoryId = UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b");

            when(entityHelper.getCurrentUser()).thenReturn(professor.getUser());


            User professorUser = professor.getUser();
            Authentication auth = new UsernamePasswordAuthenticationToken(professorUser, null, List.of(new SimpleGrantedAuthority("ROLE_ProfAcc")));
            SecurityContextHolder.getContext().setAuthentication(auth);

            when(entityHelper.findQuestionsByLaboratory(laboratory)).thenReturn(List.of(question));

            when(entityHelper.findLaboratoryById(laboratoryId)).thenReturn(laboratory);

            when(questionRepository.findAllByLaboratory(any(Laboratory.class))).thenReturn(List.of(question));

            when(dtoMapper.questionToQuestionDto(any(Question.class))).thenReturn(questionDto);


            List<QuestionDto> result = questionService.getQuestions(laboratoryId);



            assertNotNull(result);
            System.out.println(result);
            assertEquals(1, result.size());
            assertEquals(questionDto.getTitle(), result.get(0).getTitle());

            System.out.println("Get Questions Test Passed for ProfAcc Role. Result: " + result);
        }
    }
}