package ru.otus.work3.service;

import ru.otus.work3.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    public List<Question> getQuestions() throws IOException;
    public int askQuestion(Question question);
}
