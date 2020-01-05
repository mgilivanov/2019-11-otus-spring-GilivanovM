package ru.otus.work4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.work4.domain.Question;
import ru.otus.work4.service.QuestionService;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DisplayName("Класс QuestionService")
public class QuestionServiceTest {
    @Autowired
    private QuestionService questionService;

    @Test
	@DisplayName("заполняется списком вопросов")
    public void loadQuestionsTest() throws IOException {
		List<Question> questions = questionService.getQuestions();
		assertTrue(questions.size() > 0);
    }

}
