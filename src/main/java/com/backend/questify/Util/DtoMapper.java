package com.backend.questify.Util;

import com.backend.questify.DTO.ProfessorDto;
import com.backend.questify.DTO.StudentDto;
import com.backend.questify.DTO.SubmissionDto;
import com.backend.questify.DTO.UserDto;
import com.backend.questify.Entity.Professor;
import com.backend.questify.Entity.Student;
import com.backend.questify.Entity.Submission;
import com.backend.questify.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

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

// StudentDto studentToStudentDto(Student student);
//
// ProfessorDto professorToProfessorDto(Professor professor);
}
