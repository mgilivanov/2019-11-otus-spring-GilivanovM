package ru.otus.work6.dao;

import ru.otus.work6.domain.Comment;

import java.util.List;

public interface CommentDao {

    void add(Comment comment);

    List<Comment> listByBookId(Long bookId);
}
