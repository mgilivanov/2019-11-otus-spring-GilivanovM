package ru.otus.work6.service;

import ru.otus.work6.domain.Comment;

public interface CommentService {
    Comment add(Long BookId, String text);
}
