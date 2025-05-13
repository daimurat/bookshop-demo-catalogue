package com.demo.bookshop.domain.repository;

import java.util.List;

import com.demo.bookshop.domain.model.Book;

public interface BookRepository {
    List<Book> listBooks();
    Book getBook(Integer id);
}
