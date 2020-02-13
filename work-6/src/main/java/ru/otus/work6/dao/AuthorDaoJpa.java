package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import ru.otus.work6.domain.Author;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorDaoJpa implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

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

    public Optional<Author> findById(long id){
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> list() {
        return em.createQuery(
                "select a from Author a"
                , Author.class).getResultList();
    }

    @Override
    public void update(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
        } else {
            em.merge(author);
        }
    }
}
