package ru.otus.homework.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Student;

import java.util.Locale;

@Service
@PropertySource("classpath:application.properties")
public class LocalizationServiceImpl implements LocalizationService {
    private final Locale locale;
    private final MessageSource messageSource;
    public LocalizationServiceImpl(@Value("${locale.language}")String language, @Value("${locale.country}")String country, MessageSource messageSource){
        this.messageSource = messageSource;
        this.locale = new Locale(language, country);
    }
    public String getStudentLastName() {
        return messageSource.getMessage("student.lastname", new String[]{}, locale);
    }
    public String getStudentFirstName() {
        return messageSource.getMessage("student.firstname", new String[]{}, locale);
    }

    public String getTestingResults(Student student, String result){
        return messageSource.getMessage("testing.results", new String[]{student.getLastName(), student.getFirstName(), result}, locale);
    }

    public String getQuestionsFile(){
        return messageSource.getMessage("questions.file", new String[]{}, locale);
    }
}
