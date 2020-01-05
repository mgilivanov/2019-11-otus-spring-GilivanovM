package ru.otus.work4.dao;

import ru.otus.work4.domain.Question;
import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    public List<Question> loadQuestions() throws IOException;
}
