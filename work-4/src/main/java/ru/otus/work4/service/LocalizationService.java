package ru.otus.work4.service;

import ru.otus.work4.domain.Student;

public interface LocalizationService {
    public String getStudentLastName();

    public String getStudentFirstName();

    public String getTestingResults(Student student, String result);

    public String getQuestionsFile();

}
