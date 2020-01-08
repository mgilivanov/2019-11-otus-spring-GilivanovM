package ru.otus.work4.service;

import ru.otus.work4.domain.Question;

import java.io.IOException;
import java.util.List;

public interface QuestionService {
    public List<Question> getQuestions() throws IOException;

    public int askQuestion(Question question);
}
