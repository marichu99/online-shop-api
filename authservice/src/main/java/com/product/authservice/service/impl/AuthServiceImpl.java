package com.product.authservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.authservice.entity.User;
import com.product.authservice.model.UserRequestDTO;
import com.product.authservice.repository.UserRepositoryI;
import com.product.authservice.service.AuthServiceI;

@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepository;

    public AuthServiceImpl(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(UserRequestDTO userRequest) {
        User user = mapUserRequestToUser(userRequest);
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    private User mapUserRequestToUser(UserRequestDTO source) {
        User target = new User();
        BeanUtils.copyProperties(source, target);
        return target;

    }

    @Override
    public User registerUser(UserRequestDTO user) {
        User userTobeSaved = new User();
        BeanUtils.copyProperties(user, userTobeSaved);
        return userRepository.save(userTobeSaved);
    }

}
