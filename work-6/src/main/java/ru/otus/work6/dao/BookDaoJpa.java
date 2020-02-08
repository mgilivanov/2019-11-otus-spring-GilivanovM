package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.work6.domain.Author;
import ru.otus.work6.domain.Book;
import ru.otus.work6.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> find(String bookName, String authorName, String genreName) {
        TypedQuery<Book> query = em.createQuery(
                "select distinct b from Book b " +
                        "left join b.authors aa " +
                        "left join b.genres gg "+
                        "where upper(b.name) like :l_book_name " +
                        "and (:author_name is null or upper(aa.name) like :l_author_name) " +
                        "and (:genre_name is null or upper(gg.name) like :l_genre_name) "
                , Book.class);
        query.setParameter("l_book_name", "%"+bookName.toUpperCase()+"%");
        query.setParameter("l_author_name", "%"+authorName.toUpperCase()+"%");
        query.setParameter("l_genre_name", "%"+genreName.toUpperCase()+"%");
        query.setParameter("author_name", authorName);
        query.setParameter("genre_name", genreName);
        return query.getResultList();
    }

    @Override
    public void add(Book book) {
        for (Author author : book.getAuthors()) {
            if (author.getId() <= 0) {
                em.persist(author);
            } else {
                em.merge(author);
            }
        }
        for (Genre genre : book.getGenres()) {
            if (genre.getId() <= 0) {
                em.persist(genre);
            } else {
                em.merge(genre);
            }
        }
        if (book.getId() <= 0) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from Book b " +
                "where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate() == 1;
    }
}
