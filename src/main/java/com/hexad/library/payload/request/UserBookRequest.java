package com.hexad.library.payload.request;

import com.hexad.library.entity.BookRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class UserBookRequest {

    @NotBlank
    String name;

    @NotBlank
    List<BookRequest> bookRequestList;
}
