package com.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.service.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}