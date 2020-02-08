package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class CommentDaoJpa implements CommentDao {
    @PersistenceContext
    private EntityManager em;
    @Override
    public void add(Comment comment) {
        if (comment.getBook().getId() <= 0) {
            em.persist(comment.getBook());
        } else {
            em.merge(comment.getBook());
        }
        if (comment.getId() <= 0) {
            em.persist(comment);
        } else {
            em.merge(comment);
        }
    }

    @Override
    public List<Comment> listByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery(
                "select c from Comment c " +
                        "join c.book b " +
                        "where b.id = :book_id "
                , Comment.class);
        query.setParameter("book_id", bookId);
        return query.getResultList();
    }


}
