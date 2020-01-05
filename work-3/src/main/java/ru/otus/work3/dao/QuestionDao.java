package ru.otus.work3.dao;

import ru.otus.work3.domain.Question;
import java.io.IOException;
import java.util.List;

public interface QuestionDao {
    public List<Question> loadQuestions() throws IOException;
}
