package ru.otus.work5.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.work5.domain.Author;
import ru.otus.work5.domain.Book;
import ru.otus.work5.domain.Genre;
import ru.otus.work5.extractor.BookResultSetExtractor;

import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;
    private final BookResultSetExtractor bookResultSetExtractor;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, BookResultSetExtractor bookResultSetExtractor) {
        this.jdbc = jdbc;
        this.bookResultSetExtractor = bookResultSetExtractor;
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
        return new ArrayList( jdbc.query("select b.id, b.name, " +
                               "aa.id as author_id, aa.name as author_name, " +
                               "gg.id as genre_id, gg.name as genre_name, "+
                               "from books b " +
                               "left join book_authors ba on ba.book_id = b.id " +
                        "left join authors aa on aa.id = ba.author_id " +
                        "left join book_genres bg on bg.book_id = b.id " +
                        "left join genres gg on gg.id = bg.genre_id "+
                              "where upper(b.name) like :l_book_name " +
                                "and (:author_name is null or b.id in (" +
                                   "select ba.book_id " +
                                     "from authors a " +
                                     "join book_authors ba on ba.author_id=a.id " +
                                    "where upper(a.name) like :l_author_name)) " +
                                "and (:genre_name is null or b.id in (" +
                                  "select bg.book_id " +
                                    "from genres g " +
                                    "join book_genres bg on bg.genre_id=g.id " +
                                   "where upper(g.name)  like :l_genre_name)) " +
                                   "order by b.id", params, bookResultSetExtractor).values());
    }

    @Override
    public boolean deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update("delete from book_genres g where g.book_id = :id", params);
        jdbc.update("delete from book_authors a where a.book_id = :id", params);
        return jdbc.update("delete from books b where b.id = :id", params) == 1;
    }
}
