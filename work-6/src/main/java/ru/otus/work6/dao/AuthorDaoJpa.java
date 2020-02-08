package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Author> findByBookId(long id) {
        TypedQuery<Author> query = em.createQuery(
                "select a " +
                        "from Book b join b.authors a " +
                        "where b.id = :p1"
                , Author.class);
        query.setParameter("p1", id);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "select a from Author a where a.name = :name"
                , Author.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> list() {
        return em.createQuery(
                "select a from Author a"
                , Author.class).getResultList();
    }
}
