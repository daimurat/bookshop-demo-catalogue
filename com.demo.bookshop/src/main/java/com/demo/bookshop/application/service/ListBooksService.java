package com.demo.bookshop.application.service;

import java.util.List;

import com.demo.bookshop.application.dto.BookDTO;
import com.demo.bookshop.domain.model.Book;
import com.demo.bookshop.domain.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ListBooksService {
    @Inject
    BookRepository bookRepository;

    public List<BookDTO> handle() {
        List<Book> books = bookRepository.listBooks();
        return books.stream()
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice()))
                .toList();
    }
}
