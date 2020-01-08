package ru.otus.work4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.work4.domain.Question;
import ru.otus.work4.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Класс Student")

public class StudentTest {
    @DisplayName("корректно создаётся конструктором")
    @Test
    void shouldCorrectConstructor(){
        Student student = new Student("lst","frst");
        Question question = new Question("Столица РФ?","Москва");
        assertEquals("lst", student.getLastName());
        assertEquals("frst",student.getFirstName());
    }
}
