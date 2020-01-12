package ru.otus.work5.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.work5.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;


public class AuthorMapper implements RowMapper<Author> {

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Author(id, name);
    }

}
