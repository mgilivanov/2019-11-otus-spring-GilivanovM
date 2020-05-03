package ru.otus.work18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@SpringBootApplication
@EnableCircuitBreaker
public class Work18Application {
	public static void main(String[] args) {
		SpringApplication.run(Work18Application.class, args);
	}
}
