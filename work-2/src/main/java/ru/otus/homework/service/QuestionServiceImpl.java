package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.dao.QuestionDao;
import ru.otus.homework.domain.Question;

import java.io.IOException;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final UserInterfaceService userInterfaceService;
    public QuestionServiceImpl(QuestionDao questionDao, UserInterfaceService userInterfaceService){
        this.questionDao = questionDao;
        this.userInterfaceService = userInterfaceService;
    }
    public List<Question> getQuestions() throws IOException {
        return this.questionDao.loadQuestions();
    }
    public int askQuestion(Question question){
        String answer;
        answer = this.userInterfaceService.getInput(question.getText());
        if (answer.toUpperCase().equals(question.getAnswer().toUpperCase())){
            return 1;
        }
        else {
            return 0;
        }
    }
}
