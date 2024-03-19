package com.product.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{
    
}
