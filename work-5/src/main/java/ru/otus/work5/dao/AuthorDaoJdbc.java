package ru.otus.work5.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.work5.domain.Author;
import ru.otus.work5.mapper.AuthorMapper;

import java.util.List;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Author> findByBookId(long id) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("book_id", id);
        return jdbc.query("select a.id, a.name from authors a " +
                "join book_authors ba on ba.author_id = a.id " +
                "where ba.book_id=:book_id", sqlParameterSource, new AuthorMapper());
                }

    @Override
    public Author findByName(String name) {
       MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("name", name);
       try {
           return jdbc.queryForObject("select a.id, a.name from authors a where a.name = :name", sqlParameterSource, new AuthorMapper());
       }
       catch (EmptyResultDataAccessException e){
           return null;
       }
    }

    @Override
    public void add(Author author) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("name", author.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into authors(name) values(:name)", sqlParameterSource, keyHolder);
        author.setId(keyHolder.getKey().longValue());
    }

    @Override
    public List<Author> list(){
        return jdbc.query("select a.id, a.name from authors a", new AuthorMapper());
    };
}
