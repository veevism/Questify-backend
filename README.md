# Questify Application System Description

## Overview:
Questify is an educational platform that facilitates interaction between professors and students through the organization of classroom-based activities, question exercises, and problem-solving sessions. The system is built to support user registration, role assignment, class scheduling, lab conducting, and submission reviewing.

## User Registration and Roles:
Users register on the platform with unique usernames and emails and are required to set a password. Upon registration, users must select a role: either Student or Professor. This role determines the functionalities available to them within the platform.

## Professors and Classrooms:
Professors can create classrooms, which are essentially courses or sessions. Each classroom has a title, description, and scheduled start and end times.
Once a classroom is created, professors can enroll students into these classrooms. The enrollment records are maintained in the Activity entity (formerly known as Enrollment).

## Classroom Activities:
Activities within a classroom are scheduled sessions that students can attend. These sessions may involve discussions, lectures, or question exercises.
Each activity is linked to a specific classroom and has associated students.

## Laboratories and Problems:
Laboratories are specialized activities held within the context of a classroom session. Each question has a title, description, and specific start and end times.
Every question is associated with a Problem, which defines a specific challenge or task to be solved by the students. Problems include details like the problem statement, input/output formats, and sample inputs/outputs.
Problems also have multiple Test Cases, which are used to validate the solutions submitted by students. Each test case includes specific inputs and the expected outputs.

## Student Interaction and Submissions:
Students participate in laboratories by submitting their solutions to the assigned problems. Submissions include the student’s code snippets and the programming language used.
Each submission records the time when the work began, when it was submitted, and optionally when it was completed.

## Database and Relationships:
The system leverages a relational database with entities such as Users, Professors, Students, Classrooms, Activities, Laboratories, Problems, Test Cases, and Submissions.
Relationships are carefully managed to ensure data integrity and facilitate queries across different entities. For instance, professors are linked to classrooms, classrooms to activities, activities to students, and laboratories to problems.

## Functionality and User Experience:
The application is designed to be user-friendly, providing a seamless interface for professors to manage classrooms and for students to engage in educational activities.
Detailed feedback and results are provided for question submissions, including the assessment based on predefined test cases.

## Security and Performance:
The platform ensures the security of user data with encrypted passwords and controlled access based on user roles.
Performance is optimized through efficient query design and the judicious use of database indexing to speed up data retrieval.