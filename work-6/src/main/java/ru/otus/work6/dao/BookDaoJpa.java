package ru.otus.work6.dao;

import org.springframework.stereotype.Repository;
import ru.otus.work6.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoJpa implements BookDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findByName(String bookName) {
        List<Book> books = em
                .createQuery(
                        "select b " +
                                "from Book b " +
                                "where upper(b.name) like :b_name", Book.class)
                .setParameter("b_name", "%"+bookName.toUpperCase()+"%")
                .getResultList();
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = em
                .createQuery(
                        "select b " +
                                "from Book b ", Book.class)
                .getResultList();
        return books;
    }

    @Override
    public void add(Book book) {
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
        Optional<Book> book = findById(id);
        if (book.isEmpty()){
            return false;
        }
        em.remove(book.get());
        return true;
    }
}
