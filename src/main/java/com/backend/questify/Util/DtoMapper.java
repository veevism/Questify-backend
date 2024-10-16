package com.backend.questify.Util;

import com.backend.questify.DTO.*;
import com.backend.questify.DTO.User.UserDto;
import com.backend.questify.DTO.testcase.TestCaseDto;
import com.backend.questify.Entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Mapper
public interface DtoMapper {
 DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

 // --------User
 List<UserDto> userToUserDto(List<User> user);
 User UserDtoToUser (UserDto userDto);
 @Mapping(source = "student", target = "student")
 @Mapping(source = "professor", target = "professor")
 UserDto userToUserDto(User user);
 // --------

// --------Submission
 @Mapping(source = "student.studentId", target = "studentId")
 SubmissionDto submissionToSubmissionDto (Submission submission);

// default SubmissionDto mapSubmissionToDtoWithRemainingTime(Submission submission) {
//  SubmissionDto dto = submissionToSubmissionDto(submission);
//  if (submission.getStartTime() != null && submission.getQuestion() != null) {
//   LaboratoryDto lab = dto.getLaboratory();
//   if (lab != null && lab.getDurationTime() != null) {
//    LocalDateTime endTime = submission.getStartTime().plusSeconds(lab.getDurationTime());
//    dto.setRemainingTime((int) ChronoUnit.SECONDS.between(LocalDateTime.now(), endTime));
//   }
//  }
//  return dto;
// }
 // --------

 // --------Report
 ReportDto reportToReportDto(Report report);

 List<ReportDto> reportToReportDto(List<Report> reportDto);

 Report reportDtoToReport(ReportDto reportDto);
 //

 // -----Logging

 LoggingDto loggingToLoggingDto(Logging logging);
 //

 // --------Professor
@Mapping(source = "user.email", target = "email")
@Mapping(source = "user.firstName_EN" ,target = "firstName_EN")
@Mapping(source = "user.displayName" ,target = "displayName")
ProfessorDto professorToProfessorDto(Professor professor);

 List<ProfessorDto> professorToProfessorDto(List<Professor> professor);

// --------

// --------Student
 @Mapping(source = "user.email", target = "email")
 @Mapping(source = "user.firstName_EN" ,target = "firstName_EN")
 @Mapping(source = "user.displayName" ,target = "displayName")
 StudentDto studentToStudentDto (Student student);
 List<StudentDto> studentToStudentDto(List<Student> student);
// --------

 // -------Question
 List<QuestionDto> questionToQuestionDto ( List<Question> questions);

 // @Mapping(target = "testCaseQuantity", expression = "java(laboratory.getTestCases().size())")
 QuestionDto questionToQuestionDto (Question question);

 // --------

 // --------Laboratory
 @Mapping(target = "studentQuantity", expression = "java(laboratory.getStudents().size())")
 List<LaboratoryDto> laboratoryToLaboratoryDto ( List<Laboratory> laboratories);

 @Mapping(target = "studentQuantity", expression = "java(laboratory.getStudents().size())")
 LaboratoryDto laboratoryToLaboratoryDto (Laboratory laboratory);
//


 // --------TestCase
 TestCaseDto testCaseToTestCaseDto (TestCase testCase);

 List<TestCaseDto> testCaseToTestCaseDto (List<TestCase> testCase);
//


}
