package com.product.authservice.service;

import com.product.authservice.model.UserRequestDTO;
import com.product.authservice.model.UserResponse;

public interface AuthServiceI {
    UserResponse login(UserRequestDTO userRequest);
    UserResponse registerUser(UserRequestDTO user);
}
