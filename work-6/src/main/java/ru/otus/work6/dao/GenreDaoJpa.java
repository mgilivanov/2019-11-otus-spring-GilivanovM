package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<Genre> findByBookId(long id) {
        TypedQuery<Genre> query = em.createQuery(
                "select g " +
                        "from Book b join b.genres g " +
                        "where b.id = :p1"
                , Genre.class);
        query.setParameter("p1", id);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery(
                "select g from Genre g where g.name = :name"
                , Genre.class);
        query.setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> list() {
        return em.createQuery(
                "select g from Genre g"
                , Genre.class).getResultList();
    }
}
