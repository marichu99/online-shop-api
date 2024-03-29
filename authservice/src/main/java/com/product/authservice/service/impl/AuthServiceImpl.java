package com.product.authservice.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.authservice.entity.User;
import com.product.authservice.model.UserRequestDTO;
import com.product.authservice.model.UserResponse;
import com.product.authservice.repository.UserRepositoryI;
import com.product.authservice.service.AuthServiceI;
import com.product.authservice.utils.HashTextI;

@Service
public class AuthServiceImpl implements AuthServiceI {

    private final UserRepositoryI userRepository;
    private final HashTextI hashText;

    public AuthServiceImpl(UserRepositoryI userRepository, HashTextI hashText) {
        this.userRepository = userRepository;
        this.hashText=hashText;
    }

    @Override
    public UserResponse login(UserRequestDTO userRequest) {
        User requestedUser = mapUserRequestToUser(userRequest);
        String hashedPassword = hashText.hash(requestedUser.getPassword());
        System.out.println("The hashed password is "+hashedPassword);
        UserResponse foundUser =userRepository.findByUsernameAndPassword(requestedUser.getUsername(), hashedPassword);
        return foundUser;
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
    public UserResponse registerUser(UserRequestDTO userRequestDTO) {
        User userTobeSaved = mapUserRequestToUser(userRequestDTO);
        userTobeSaved.setPassword(hashText.hash(userTobeSaved.getPassword()));
        return mapUserToUserResponse(userTobeSaved);
    }

}
