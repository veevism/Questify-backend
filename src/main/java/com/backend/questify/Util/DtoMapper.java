package com.backend.questify.Util;

import com.backend.questify.DTO.*;
import com.backend.questify.Entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DtoMapper {
 DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

 User UserDtoToUser (UserDto userDto);

 @Mapping(source = "student", target = "student")
 @Mapping(source = "professor", target = "professor")
 UserDto userToUserDto(User user);
//

 List<UserDto> userToUserDto(List<User> user);

 @Mapping(source = "student.studentId", target = "studentId")
 SubmissionDto submissionToSubmissionDto (Submission submission);
// Optional<SubmissionDto> submissionToSubmissionDto (Submission submission);



// StudentDto studentToStudentDto(Student student);
//
ProfessorDto professorToProfessorDto(Professor professor);
 List<ProfessorDto> professorToProfessorDto(List<Professor> professor);

// @Mapping(source = "classroom.professor", target = "professor")
// @Mapping(source = "classroom.classroomId", target = "classroomId")
// @Mapping(source = "classroom.title", target = "title")
// @Mapping(source = "classroom.description", target = "description")
// @Mapping(source = "classroom.isActive", target = "isActive")
// @Mapping(source = "classroom.invitationCode", target = "invitationCode")
 ClassroomDto classroomToClassroomDto(Classroom classroom);

 List<ClassroomDto> classroomToClassroomDto (List<Classroom> classroom);

 AssignmentDto assignmentToAssignmentDto (Assignment assignment);
// List<AssignmentDto> assignmentToAssignmentDto (List<AssignmentDto> assignment);

 List<AssignmentDto> assignmentToAssignmentDto(List<Assignment> assignment);

 List<LaboratoryDto> laboratoryToLaboratoryDto ( List<Laboratory> laboratory);

 @Mapping(target = "testCaseQuantity", expression = "java(laboratory.getTestCases().size())")
 LaboratoryDto laboratoryToLaboratoryDto (Laboratory laboratory);




}
