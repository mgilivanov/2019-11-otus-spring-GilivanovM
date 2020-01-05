package ru.otus.work3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.work3.service.TestingService;

import java.io.IOException;

@SpringBootApplication
public class Work3Application {

	public static void main(String[] args) throws IOException {
		ApplicationContext context = SpringApplication.run(Work3Application.class, args);
		TestingService service = context.getBean(TestingService.class);
		service.run();
	}

}
