package ru.otus.homework.service;

import ru.otus.homework.domain.Student;

import java.util.Locale;

public interface LocalizationService {
    public String getStudentLastName();
    public String getStudentFirstName();
    public String getTestingResults(Student student, String result);
    public String getQuestionsFile();
}
