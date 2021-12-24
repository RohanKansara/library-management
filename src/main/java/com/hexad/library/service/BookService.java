package com.hexad.library.service;

import com.hexad.library.entity.Book;
import com.hexad.library.exception.ResourceNotFoundException;
import com.hexad.library.payload.response.BookResponse;

public interface BookService {

    BookResponse findAllBooks();

    Book findBookById(Long id) throws ResourceNotFoundException;

    Book saveBook(Book book);

    Book updateBook(Book book);

    void deleteBook(Long id) throws ResourceNotFoundException;
}
