package ru.otus.work7.service;

import ru.otus.work7.domain.Comment;

public interface CommentService {
    Comment add(Long BookId, String text);
}
