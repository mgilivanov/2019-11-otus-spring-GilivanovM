package ru.otus.work7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.work7.domain.Comment;

public interface CommentRepository  extends JpaRepository<Comment, Long> {
    Comment save(Comment comment);
}
