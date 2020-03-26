package ru.otus.work9.service;

import ru.otus.work9.domain.Comment;

public interface CommentService {
    Comment add(Long BookId, String text);
}
