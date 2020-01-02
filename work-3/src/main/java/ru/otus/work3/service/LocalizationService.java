package ru.otus.work3.service;

import ru.otus.work3.domain.Student;

public interface LocalizationService {
    public String getStudentLastName();
    public String getStudentFirstName();
    public String getTestingResults(Student student, String result);
    public String getQuestionsFile();
}
