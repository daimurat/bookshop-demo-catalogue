package com.demo.bookshop.application.service;

import com.demo.bookshop.application.dto.BookDTO;
import com.demo.bookshop.domain.model.Book;
import com.demo.bookshop.domain.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetBookService {
    @Inject
    BookRepository bookRepository;

    public BookDTO handle(Integer bookId) {
        Book book = bookRepository.getBook(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice());
    }
}
