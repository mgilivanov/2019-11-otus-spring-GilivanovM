package ru.otus.work10.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.work10.domain.Author;
import ru.otus.work10.domain.Book;
import ru.otus.work10.domain.Comment;
import ru.otus.work10.domain.Genre;

import java.util.Date;

@ChangeLog
public class DatabaseChangelog {

    public DatabaseChangelog() {
    }

    @ChangeSet(order = "001", id = "addBooks", author = "mgilivanov")
    public void addBooks(final MongoTemplate mongoTemplate) {
        Book book = new Book("1", "War and Peace", new Author("Lev Tolstoy"), new Genre("Novel"));
        book.addComment(new Comment("comment1", new Date()));
        book.addComment(new Comment("comment2", new Date()));
        mongoTemplate.save(book);
    }
}
