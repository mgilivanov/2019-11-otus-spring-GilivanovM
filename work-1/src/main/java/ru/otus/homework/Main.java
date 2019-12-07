package ru.otus.homework;

import au.com.bytecode.opencsv.CSVReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.homework.service.TestingService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestingService service = context.getBean(TestingService.class);
        service.run();

    }
}
