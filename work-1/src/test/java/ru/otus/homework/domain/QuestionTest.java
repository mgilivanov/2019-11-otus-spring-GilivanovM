package ru.otus.homework.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Question")

public class QuestionTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldCorrectConstructor(){
        Question question = new Question("Столица РФ?","Москва");
        assertEquals("Столица РФ?", question.getText());
        assertEquals("Москва",question.getAnswer());
    }
}
