package ru.otus.homework.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.domain.Student;

import java.io.IOException;
import java.util.List;

@Service
public class TestingServiceImpl implements TestingService{
    private final StudentService studentService;
    private final UserInterfaceService userInterfaceService;
    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final LocalizationService localizationService;
    public TestingServiceImpl(StudentService studentService, UserInterfaceService userInterfaceService, QuestionService questionService, MessageSource messageSource, LocalizationService localizationService){
        this.studentService=studentService;
        this.userInterfaceService = userInterfaceService;
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.localizationService = localizationService;
    }
    public void run() throws IOException {
        Student student;
        student = studentService.getStudent();
        List<Question> questions = this.questionService.getQuestions();
        int result = 0;
        for (Question question : questions){
            result = result + questionService.askQuestion(question);
        }
        this.userInterfaceService.sendMessage(localizationService.getTestingResults(student, result + "/" + questions.size()));
    }
}
