package ru.otus.work5.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.work5.domain.Genre;
import ru.otus.work5.mapper.GenreMapper;

import java.util.List;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Genre> findByBookId(long id) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("book_id", id);
        return jdbc.query("select g.id, g.name from genres g " +
                "join book_genres bg on bg.genre_id = g.id " +
                "where bg.book_id=:book_id", sqlParameterSource, new GenreMapper());
    }

    @Override
    public Genre findByName(String name) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("name", name);
        try {
            return jdbc.queryForObject("select g.id, g.name from genres g where g.name = :name", sqlParameterSource, new GenreMapper());
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public void add(Genre genre) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource().addValue("name", genre.getName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update("insert into genres(name) values(:name)", sqlParameterSource, keyHolder);
        genre.setId(keyHolder.getKey().longValue());
    }

    @Override
    public List<Genre> list(){
        return jdbc.query("select g.id, g.name from genres g", new GenreMapper());
    };
}
