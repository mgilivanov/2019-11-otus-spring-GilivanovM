package ru.otus.work5.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.otus.work5.domain.Author;
import ru.otus.work5.domain.Book;
import ru.otus.work5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class BookResultSetExtractor implements
        ResultSetExtractor<Map<Long, Book>> {
    @Override
    public Map<Long, Book> extractData(ResultSet rs) throws SQLException,
            DataAccessException {
        Map<Long, Book> books = new HashMap<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            Book book = books.get(id);
            if (book == null) {
                book = new Book(id, rs.getString("name"));
                books.put(book.getId(), book);
            }
            long authorId = rs.getLong("author_id");
            Author author = new Author(authorId, rs.getString("author_name"));
            if (!book.getAuthors().contains(author)) {
                book.addAuthor(author);
            }
            long genreId = rs.getLong("genre_id");
            Genre genre = new Genre(genreId, rs.getString("genre_name"));
            if (!book.getGenres().contains(genre)) {
                book.addGenre(genre);
            }
        }
        return books;
    }
}