package com.hexad.library.controller;

import com.hexad.library.entity.Book;
import com.hexad.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/book/")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/add-book")
    public Book saveBook(Book book) {

        return bookService.saveBook(book);
    }

}
