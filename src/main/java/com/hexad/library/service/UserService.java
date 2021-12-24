package com.hexad.library.service;

import com.hexad.library.entity.User;
import com.hexad.library.payload.request.UserBookRequest;
import com.hexad.library.payload.response.UserResponse;

public interface UserService {

    User findUserByName(String name);

    UserResponse borrowBook(UserBookRequest userBookRequest);

    UserResponse returnBook(UserBookRequest userBookRequest);
}
