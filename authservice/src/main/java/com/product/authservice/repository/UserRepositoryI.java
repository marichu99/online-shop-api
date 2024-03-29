package com.product.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.product.authservice.entity.User;
import com.product.authservice.model.UserResponse;

public interface UserRepositoryI extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM user_tbl WHERE username = ?1 AND password = ?2", nativeQuery = true)
    UserResponse findByUsernameAndPassword(String username, String password);


}
