package ru.otus.homework.dao;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.core.io.Resource;
import ru.otus.homework.domain.Question;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao{
    private final Resource questionsFile;
    public QuestionDaoImpl(Resource questionsFile){
        this.questionsFile = questionsFile;
    }
    public List<Question> loadQuestions() throws IOException {
        List<Question> questions = new ArrayList<>();
        CSVReader reader = new CSVReader(new InputStreamReader(this.questionsFile.getInputStream()), ';', '"', 0);
        java.util.List<String[]> allRows = reader.readAll();
        for(String[] row : allRows){
            Question question = new Question(row[0],row[1]);
            questions.add(question);
        }
        return questions;
    }
}
