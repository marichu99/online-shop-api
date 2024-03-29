package com.product.authservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.authservice.entity.User;
import com.product.authservice.model.UserRequestDTO;
import com.product.authservice.model.UserResponse;
import com.product.authservice.repository.UserRepositoryI;
import com.product.authservice.service.AuthServiceI;
import com.product.authservice.utils.HashText;

@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepository;

    @Autowired
    HashText hashText;

    public AuthServiceImpl(UserRepositoryI userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse login(UserRequestDTO userRequest) {
        User requestedUser = mapUserRequestToUser(userRequest);
        User foundUser =userRepository.findByUsernameAndPassword(requestedUser.getUsername(), hashText.hash(requestedUser.getPassword()));
        return mapUserToUserResponse(foundUser);
    }

    private User mapUserRequestToUser(UserRequestDTO source) {
        User target = new User();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    private UserResponse mapUserToUserResponse(User source) {
        UserResponse target = new UserResponse();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public UserResponse registerUser(UserRequestDTO user) {

        User userTobeSaved = mapUserRequestToUser(user);
        userTobeSaved.setPassword(hashText.hash(userTobeSaved.getPassword()));
        System.out.println("****************************");
        System.out.println((userTobeSaved.getPassword()));
        System.out.println("****************************");    
        return mapUserToUserResponse(userRepository.save(userTobeSaved));
    }

}
