package com.hexad.library.controller;

import com.hexad.library.payload.request.UserBookRequest;
import com.hexad.library.payload.response.UserResponse;
import com.hexad.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(value = "/borrow-book", consumes = "application/json", produces = "application/json")
    public UserResponse borrowBook(@RequestBody UserBookRequest userBookRequest) {
        return userService.borrowBook(userBookRequest);
    }

    @PostMapping(value = "/return-book", consumes = "application/json", produces = "application/json")
    public UserResponse returnBook(@RequestBody UserBookRequest userBookRequest) {
        return userService.returnBook(userBookRequest);
    }
}
