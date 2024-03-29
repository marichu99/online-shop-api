package com.product.authservice.service;

import com.product.authservice.entity.User;
import com.product.authservice.model.UserRequestDTO;

public interface AuthServiceI {
    User login(UserRequestDTO userRequest);
    User registerUser(UserRequestDTO user);
}
