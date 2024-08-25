package com.backend.questify.Util;

import com.backend.questify.Entity.*;
import com.backend.questify.Model.Role;
import com.backend.questify.Model.Status;
import com.backend.questify.Model.SubmissionStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static reactor.core.publisher.Mono.when;

public class TestSetupUtil {

    public static Professor setupProfessor() {
        Professor professor = new Professor();
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

        return professor;
    }

    public static Student setupStudent() {
        Student student = new Student();
        student.setStudentId(642115031L);

        User studentUser = User.builder()
                .userId(642115031L)
                .userName("phiriyakorn_m")
                .firstName_EN("PHIRIYAKORN")
                .lastName_EN("MANEEKONGRIT")
                .displayName("PHIRIYAKORN MANEEKONGRIT")
                .email("phiriyakorn_m@cmu.ac.th")
                .organization_name_EN("College of Arts, Media and Technology")
                .role(Role.StdAcc)
                .student(student)
                .build();

        student.setUser(studentUser);

        return student;
    }

    public static Laboratory setupLaboratory(Professor professor, Student student) {
        Laboratory laboratory = Laboratory.builder()
                .laboratoryId(UUID.fromString("c722759c-f1e1-43ea-9097-97598641539b"))
                .title("Learn Calculation Sequence In Programming")
                .description("In this laboratory, we will provide you with a question and need you to do basic calculation on each shape such as Triangle and Square")
                .maxScore(100)
                .status(Status.PUBLISH)
                .durationTime(600)
                .professor(professor)
                .studentQuestion(new HashMap<>())
                .build();

        laboratory.getStudentQuestion().put(student.getStudentId(), UUID.fromString("c2f7ae68-7f07-49c9-93a4-4f47d182f7eb"));

        return laboratory;
    }

    public static Question setupQuestion(Laboratory laboratory, Professor professor) {
        return Question.builder()
                .questionId(UUID.fromString("c2f7ae68-7f07-49c9-93a4-4f47d182f7eb"))
                .title("Version 1: Calculate the Area of a Rectangle")
                .problemStatement("You are given two positive integers, length and width, representing the dimensions of a rectangle. Your task is to implement a function calculateArea(length: int, width: int) -> int that returns the area of the rectangle.")
                .laboratory(laboratory)
                .professor(professor)
                .build();
    }

    public static TestCase setupTestCase1(Question question) {
        return TestCase.builder()
                .testCaseId(UUID.fromString("a8afac9a-8246-40cc-acc5-94fa75619ba2"))
                .input("100 25")
                .expectedOutput("2500")
                .question(question)
                .build();
    }

    public static TestCase setupTestCase2(Question question) {
        return TestCase.builder()
                .testCaseId(UUID.fromString("bb59fafc-641c-4700-8bc1-b36c6293ffca"))
                .input("10 5")
                .expectedOutput("50")
                .question(question)
                .build();
    }

    public static TestCaseResult setupTestCaseResult1(Submission submission, TestCase testCase1) {
        return TestCaseResult.builder()
                .testCaseResultId(UUID.fromString("cc12ab15-7149-48e4-851d-6daa421f9672"))
                .testCase(testCase1)
                .submission(submission)
                .actualOutput("2500")  // Correct output for testCase1
                .result("PASS")
                .build();
    }

    public static TestCaseResult setupTestCaseResult2(Submission submission, TestCase testCase2) {
        return TestCaseResult.builder()
                .testCaseResultId(UUID.fromString("8dbe3bc2-88f8-4c4e-960c-714a20064dfa"))
                .testCase(testCase2)
                .submission(submission)
                .actualOutput("50")  // Correct output for testCase2
                .result("PASS")
                .build();
    }

    public static Submission setupSubmission(Question question, Student student, Professor professor) {
        Submission submission = Submission.builder()
                .submissionId(UUID.fromString("dfb8197c-eded-42b2-8871-de7ce2496e18"))
                .question(question)
                .student(student)
                .professor(professor)
                .status(SubmissionStatus.ACTIVE)
                .codeSnippets(Submission.getDefaultSnippets())
                .testCaseResults(new ArrayList<>())  // Initialize with empty list
                .build();

        // Setup Test Case Results and add to the submission
        TestCase testCase1 = setupTestCase1(question);
        TestCase testCase2 = setupTestCase2(question);

        TestCaseResult testCaseResult1 = setupTestCaseResult1(submission, testCase1);
        TestCaseResult testCaseResult2 = setupTestCaseResult2(submission, testCase2);

        submission.getTestCaseResults().add(testCaseResult1);
        submission.getTestCaseResults().add(testCaseResult2);

        return submission;
    }

    public static Report setupReport(Submission submission) {
        return Report.builder()
                .reportId(UUID.randomUUID())
                .submission(submission)
                .question(submission.getQuestion())
                .loggings(new ArrayList<>())
                .maxScore(submission.getQuestion().getLaboratory().getMaxScore())
                .build();
    }



}