package ru.otus.work14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.work14.domain.Book;
import ru.otus.work14.domain.Comment;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.HashMap;
@Configuration
@RequiredArgsConstructor
public class ItemReaderConfig {
    private final MongoOperations mongoOperations;
    @StepScope
    @Bean
    public MongoItemReader<Book> bookImportMongoItemReader() {
        return new MongoItemReaderBuilder<Book>().name("bookImportMongoItemReader")
                .template(mongoOperations)
                .targetType(Book.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<Comment> commentImportMongoItemReader() {
        return new MongoItemReaderBuilder<Comment>().name("commentImportMongoItemReader")
                .template(mongoOperations)
                .targetType(Comment.class)
                .jsonQuery("{}")
                .sorts(new HashMap<>())
                .build();
    }
}
