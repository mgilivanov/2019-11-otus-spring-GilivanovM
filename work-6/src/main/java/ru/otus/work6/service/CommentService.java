package ru.otus.work6.service;

import ru.otus.work6.domain.Comment;

import java.util.List;

public interface CommentService {
    Comment add(Long BookId, String text);

    List<Comment> listByBookId(Long bookId);
}
