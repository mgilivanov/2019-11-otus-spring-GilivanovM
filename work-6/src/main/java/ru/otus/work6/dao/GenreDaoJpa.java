package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import ru.otus.work6.domain.Genre;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;

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

    public Optional<Genre> findById(long id){
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> list() {
        return em.createQuery(
                "select g from Genre g"
                , Genre.class).getResultList();
    }

    @Override
    public void update(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }
}
