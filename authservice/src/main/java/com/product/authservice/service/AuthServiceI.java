package com.product.authservice.service;

import com.product.authservice.entity.User;

public interface AuthServiceI {
    User login(User user);
    boolean registerUser(User user, String password);
}
