package ru.otus.work4.dao;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import ru.otus.work4.domain.Question;
import ru.otus.work4.service.LocalizationService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDaoImpl implements QuestionDao {
    private final MessageSource messageSource;
    private final LocalizationService localizationService;

    public QuestionDaoImpl(MessageSource messageSource, LocalizationService localizationService) {
        this.messageSource = messageSource;
        this.localizationService = localizationService;
    }

    public List<Question> loadQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        CSVReader reader = new CSVReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(localizationService.getQuestionsFile())), ';', '"', 0);
        java.util.List<String[]> allRows = reader.readAll();
        for (String[] row : allRows) {
            Question question = new Question(row[0], row[1]);
            questions.add(question);
        }
        return questions;
    }
}
