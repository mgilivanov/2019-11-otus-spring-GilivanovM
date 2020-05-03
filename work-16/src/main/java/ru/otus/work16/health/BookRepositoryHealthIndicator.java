package ru.otus.work16.health;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.work16.domain.Book;
import ru.otus.work16.repository.BookRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class BookRepositoryHealthIndicator implements HealthIndicator {

    private final BookRepository bookRepository;

    @Override
    public Health health() {
        List<Book> books = bookRepository.findAll();
        if (books.size() > 0) {
            return Health.up().withDetail("message", "Books count is OK").build();
        }
        return Health.outOfService().withDetail("message", "Alert! Books count is ZERO.").build();
    }
}
