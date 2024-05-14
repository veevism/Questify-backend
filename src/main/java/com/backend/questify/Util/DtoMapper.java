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

 ClassroomDto classroomToClassroomDto(Classroom classroom);

 List<ClassroomDto> classroomToClassroomDto (List<Classroom> classroom);


 AssignmentDto assignmentToAssignmentDto (Assignment assignment);
 List<AssignmentDto> assignmentToAssignmentDto (List<AssignmentDto> assignment);

}
