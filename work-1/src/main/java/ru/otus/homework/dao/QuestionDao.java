package ru.otus.homework.dao;

import ru.otus.homework.domain.Question;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    public List<Question> loadQuestions() throws IOException;
}
