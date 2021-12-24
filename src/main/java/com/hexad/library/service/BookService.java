package com.hexad.library.service;

import com.hexad.library.entity.Book;
import com.hexad.library.exception.ResourceNotFoundException;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();

    Book findBookById(Long id) throws ResourceNotFoundException;

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Long id) throws ResourceNotFoundException;
}
