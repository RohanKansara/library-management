package com.hexad.library.payload.response;

import com.hexad.library.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    User user;

    String status;
}
