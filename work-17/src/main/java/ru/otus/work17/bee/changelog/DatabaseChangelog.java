package ru.otus.work17.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.work17.domain.Author;
import ru.otus.work17.domain.Book;
import ru.otus.work17.domain.Genre;

@ChangeLog
public class DatabaseChangelog {

    public DatabaseChangelog() {
    }

    @ChangeSet(order = "001", id = "addBooks", author = "mgilivanov")
    public void addBooks(final MongoTemplate mongoTemplate) {
        Book book = new Book("1", "War and Peace", new Author("Lev Tolstoy"), new Genre("Novel"));
        mongoTemplate.save(book);
    }
}
