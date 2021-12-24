package com.hexad.library.controller;

import com.hexad.library.entity.Book;
import com.hexad.library.payload.response.BookResponse;
import com.hexad.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/book/")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping(value = "/add-book", consumes = "application/json", produces = "application/json")
    public Book saveBook(@RequestBody Book book) {

        return bookService.saveBook(book);
    }

    @GetMapping(value = "/view-books", produces = "application/json")
    public BookResponse viewBooks() {
        return bookService.findAllBooks();
    }

}
