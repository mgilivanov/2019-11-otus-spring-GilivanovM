package ru.otus.homework.service;

import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;

import java.io.IOException;
import java.util.List;

public class TestingServiceImpl implements TestingService{
    StudentService studentService;
    UserInterfaceService userInterfaceService;
    QuestionService questionService;
    public TestingServiceImpl(StudentService studentService, UserInterfaceService userInterfaceService, QuestionService questionService){
        this.studentService=studentService;
        this.userInterfaceService = userInterfaceService;
        this.questionService = questionService;
    }
    public void run() throws IOException {
        Student student;
        student = studentService.getStudent();
        List<Question> questions = this.questionService.getQuestions();
        int result = 0;
        for (Question question : questions){
            result = result + questionService.askQuestion(question);
        }
        this.userInterfaceService.sendMessage("Результаты тестирования для студента " + student.getLastName() + " " + student.getFirstName() + ": " + result + "/" + questions.size());
    }
}
