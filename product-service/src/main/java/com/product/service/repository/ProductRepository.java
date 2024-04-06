package com.product.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.product.service.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE tbl_products p SET p.Carted = 'CARTED' WHERE p.product_id IN (:ids)", nativeQuery = true)
    void updateCartStatusToCarted(List<Integer> ids);
}