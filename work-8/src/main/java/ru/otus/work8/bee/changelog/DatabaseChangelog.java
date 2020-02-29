package ru.otus.work8.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.work8.domain.Author;
import ru.otus.work8.domain.Book;
import ru.otus.work8.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "addBooks", author = "mgilivanov")
    public void addBooks(final MongoTemplate mongoTemplate) {
        Book book = new Book("1","War and Peace");
        Author author = new Author("1","Lev Tolstoy");
        Genre genre1 = new Genre("1","Novel");
        Genre genre2 = new Genre("2","Military prose");
        book.addAuthor(author);
        book.addGenre(genre1);
        book.addGenre(genre2);
        mongoTemplate.save(author);
        mongoTemplate.save(genre1);
        mongoTemplate.save(genre2);
        mongoTemplate.save(book);
    }
}
