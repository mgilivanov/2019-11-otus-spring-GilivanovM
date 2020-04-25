package ru.otus.work14.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.work14.domain.h2.H2Author;
import ru.otus.work14.domain.h2.H2Book;
import ru.otus.work14.domain.h2.H2Comment;
import ru.otus.work14.domain.h2.H2Genre;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ItemWriterConfig {
    private final DataSource dataSource;
    @StepScope
    @Bean
    public JdbcBatchItemWriter<H2Author> authorImportItemWriter() {
        return new JdbcBatchItemWriterBuilder<H2Author>()
                .dataSource(dataSource)
                .sql("insert into authors(name) values (?)")
                .itemPreparedStatementSetter((author, preparedStatement) -> {
                    preparedStatement.setString(1, author.getName());
                })
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<H2Genre> genreImportItemWriter() {
        return new JdbcBatchItemWriterBuilder<H2Genre>()
                .dataSource(dataSource)
                .sql("insert into genres(name) values (?)")
                .itemPreparedStatementSetter((genre, preparedStatement) -> {
                    preparedStatement.setString(1, genre.getName());
                })
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<H2Book> bookImportItemWriter() {
        return new JdbcBatchItemWriterBuilder<H2Book>()
                .dataSource(dataSource)
                .sql("insert into books(name, author_id, genre_id) select ?, (select id from authors where name = ?), (select id from genres where name = ?)")
                .itemPreparedStatementSetter((book, preparedStatement) -> {
                    preparedStatement.setString(1, book.getName());
                    preparedStatement.setString(2, book.getAuthor().getName());
                    preparedStatement.setString(3, book.getGenre().getName());
                })
                .build();
    }

    @StepScope
    @Bean
    public JdbcBatchItemWriter<H2Comment> commentImportItemWriter() {
        return new JdbcBatchItemWriterBuilder<H2Comment>()
                .dataSource(dataSource)
                .sql("insert into comments(book_id, text, create_date) select (select id from books where name = ?), ?, ?")
                .itemPreparedStatementSetter((comment, preparedStatement) -> {
                    preparedStatement.setString(1, comment.getBook().getName());
                    preparedStatement.setString(2, comment.getText());
                    preparedStatement.setDate(3, new java.sql.Date(comment.getCreateDate().getTime()));
                })
                .build();
    }
}
