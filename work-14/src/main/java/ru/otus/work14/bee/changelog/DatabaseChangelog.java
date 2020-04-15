package ru.otus.work14.bee.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.work14.domain.Author;
import ru.otus.work14.domain.Book;
import ru.otus.work14.domain.Comment;
import ru.otus.work14.domain.Genre;

import java.util.Date;

@ChangeLog
public class DatabaseChangelog {

    public DatabaseChangelog() {
    }

    @ChangeSet(order = "001", id = "addBooks", author = "mgilivanov")
    public void addBooks(final MongoTemplate mongoTemplate) {
        Book book = new Book("1", "War and Peace", new Author("Lev Tolstoy"), new Genre("Novel"));
        mongoTemplate.save(book);
        mongoTemplate.save(new Comment("1","good book",new Date(), book));
        mongoTemplate.save(new Comment("2","normal book",new Date(), book));

        mongoTemplate.save(new Book("2", "Book2", new Author("Ivanov"), new Genre("Genre2")));
        mongoTemplate.save(new Book("3", "Book3", new Author("Petrov"), new Genre("Novel")));

    }
}
