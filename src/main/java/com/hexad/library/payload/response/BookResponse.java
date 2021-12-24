package com.hexad.library.payload.response;

import com.hexad.library.entity.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponse {

    List<Book> books;
}
