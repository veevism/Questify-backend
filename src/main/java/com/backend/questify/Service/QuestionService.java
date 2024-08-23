package com.backend.questify.Service;

import com.backend.questify.DTO.QuestionDto;
import com.backend.questify.Entity.*;
import com.backend.questify.Exception.BadRequestException;
import com.backend.questify.Exception.ListIsNotEmptyException;
import com.backend.questify.Exception.ResourceNotFoundException;
import com.backend.questify.Repository.*;
import com.backend.questify.Util.DtoMapper;
import com.backend.questify.Util.EntityHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.backend.questify.Model.Role.ProfAcc;
import static com.backend.questify.Model.Role.StdAcc;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private EntityHelper entityHelper;

	public QuestionDto createQuestion (UUID laboratoryId, QuestionDto questionDto) {

		Question createdQuestion = Question.builder()
				.laboratory(entityHelper.findLaboratoryById(laboratoryId))
				.professor(entityHelper.findProfessorById(entityHelper.getCurrentUserId()))
				.title(questionDto.getTitle())
				.problemStatement(questionDto.getProblemStatement())
				.build();

		return DtoMapper.INSTANCE.questionToQuestionDto(questionRepository.save(createdQuestion));
	}

	public List<QuestionDto> getQuestions(UUID laboratoryId) {
		Laboratory existingLaboratory = entityHelper.findLaboratoryById(laboratoryId);
		if (entityHelper.getCurrentUser().getRole() == StdAcc) {
			return DtoMapper.INSTANCE.questionToQuestionDto(ListIsNotEmptyException.requireNotEmpty(questionRepository.findAllByQuestionId(existingLaboratory.getStudentQuestion().get(entityHelper.getCurrentUser().getStudent().getStudentId())), Question.class.getSimpleName()));
//			return DtoMapper.INSTANCE.questionToQuestionDto(entityHelper.findQuestionsByLaboratory(existingLaboratory));
		} else {
			// Still Need Fix
//			return DtoMapper.INSTANCE.questionToQuestionDto(ListIsNotEmptyException.requireNotEmpty(questionRepository.findAllByAssignment(assignment), Question.class.getSimpleName()));
			return DtoMapper.INSTANCE.questionToQuestionDto(entityHelper.findQuestionsByLaboratory(existingLaboratory));
		}
	}

	//
	public QuestionDto getQuestion(UUID questionId) {
		return DtoMapper.INSTANCE.questionToQuestionDto(entityHelper.findQuestionById(questionId));
	}

	public void deleteQuestion(UUID questionId) {
		entityHelper.deleteQuestionById(questionId);
	}

	@Transactional
	public QuestionDto updateQuestion(UUID questionId, QuestionDto questionDto) {

		Question question = entityHelper.findQuestionById(questionId);

			question.setTitle(questionDto.getTitle());
			question.setProblemStatement(questionDto.getProblemStatement());

		return DtoMapper.INSTANCE.questionToQuestionDto(questionRepository.save(question));

	}
}
