package com.hexad.library.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    String name;

    String isbn;

    int copies;
}
