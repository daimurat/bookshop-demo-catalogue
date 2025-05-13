package com.demo.bookshop.infrastructure.repository;

import com.demo.bookshop.domain.model.Book;
import com.demo.bookshop.domain.repository.BookRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class BookRepositoryImpl implements BookRepository{
    @Inject
    EntityManager em;

    public List<Book> listBooks() {
        return em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public Book getBook(Integer id) {
        return em.find(Book.class, id);
    }
}
