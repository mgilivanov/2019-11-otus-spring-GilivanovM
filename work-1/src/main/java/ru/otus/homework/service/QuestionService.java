package ru.otus.homework.service;

import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    public List<Question> getQuestions() throws IOException;
    public int askQuestion(Question question);
}
