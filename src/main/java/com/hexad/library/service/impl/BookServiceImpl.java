package com.hexad.library.service.impl;

import com.hexad.library.entity.Book;
import com.hexad.library.exception.ResourceNotFoundException;
import com.hexad.library.repository.BookRepository;
import com.hexad.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) throws ResourceNotFoundException {
        final Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Book not found with ID %d", id)));

        bookRepository.deleteById(book.getId());
    }
}
