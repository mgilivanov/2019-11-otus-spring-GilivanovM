package ru.otus.work5.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.work5.domain.Author;
import ru.otus.work5.domain.Book;
import ru.otus.work5.domain.Genre;
import ru.otus.work5.mapper.BookMapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void add(Book book) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("name", book.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into books(name) values(:name)", sqlParameterSource, keyHolder);
        book.setId(keyHolder.getKey().longValue());
        for (Author author : book.getAuthors()) {
            sqlParameterSource = new MapSqlParameterSource().addValue("book_id", book.getId())
                    .addValue("author_id", author.getId());
            jdbc.update("insert into book_authors(book_id,author_id) values(:book_id,:author_id)", sqlParameterSource);
        }
        for (Genre genre : book.getGenres()) {
            sqlParameterSource = new MapSqlParameterSource().addValue("book_id", book.getId())
                    .addValue("genre_id", genre.getId());
            jdbc.update("insert into book_genres(book_id, genre_id) values(:book_id,:genre_id)", sqlParameterSource);
        }
    }

    @Override
    public List<Book> find(String bookName, String authorName, String genreName) {
        Map<String, Object> params = new HashMap<>();
        params.put("l_book_name", "%"+bookName.toUpperCase()+"%");
        params.put("l_author_name", "%"+authorName.toUpperCase()+"%");
        params.put("l_genre_name", "%"+genreName.toUpperCase()+"%");
        params.put("author_name", authorName);
        params.put("genre_name",  genreName);
        return jdbc.query("select b.id, b.name from books b " +
                "where upper(b.name) like :l_book_name " +
                "and (:author_name is null or id in (select ba.book_id from authors a join book_authors ba on ba.author_id=a.id where upper(a.name) like :l_author_name)) " +
                "and (:genre_name is null or id in (select bg.book_id from genres g join book_genres bg on bg.genre_id=g.id where upper(g.name)  like :l_genre_name))", params, new BookMapper());
    }

    @Override
    public boolean deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from book_genres g where g.book_id = :id", params);
        jdbc.update("delete from book_authors a where a.book_id = :id", params);
        return jdbc.update("delete from books b where b.id = :id", params) == 1;
    }
}
