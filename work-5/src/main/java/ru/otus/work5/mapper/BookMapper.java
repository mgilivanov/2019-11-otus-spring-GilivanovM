package ru.otus.work5.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.otus.work5.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;


    public class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Book(id, name);
        }

}
