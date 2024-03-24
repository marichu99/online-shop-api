package com.product.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.payment.entity.Payment;

public interface ProductRepository extends JpaRepository<Payment, Integer>{
    
}
