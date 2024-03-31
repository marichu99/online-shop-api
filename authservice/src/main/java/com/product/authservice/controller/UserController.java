package com.product.authservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.product.authservice.model.ApiResponse;
import com.product.authservice.model.UserRequestDTO;
import com.product.authservice.model.UserResponse;
import com.product.authservice.service.AuthServiceI;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@CrossOrigin(origins =  "http://localhost:1841/") 
@RequestMapping("api/users")
public class UserController {
    private final AuthServiceI authService;

    public UserController(AuthServiceI authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ApiResponse<?> getUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponse userResponse = authService.login(userRequestDTO);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder().message("User Logged in Successfully")
                .data(userResponse).success(true).build();
        return apiResponse;
    }

    @PostMapping("signup")
    public ApiResponse<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponse userResponse = authService.registerUser(userRequestDTO);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>builder().message("User created successfully")
                .data(userResponse).success(true).build();
        return apiResponse;
    }

}
