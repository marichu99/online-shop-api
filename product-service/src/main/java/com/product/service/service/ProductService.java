package com.product.service.service;

import java.util.List;

import com.product.service.model.ProductCreateRequest;
import com.product.service.model.ProductCreateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    List<ProductCreateResponse> findAll();

    ProductCreateResponse findById(Integer productId);

    void addToCart(List<Integer> productList);
    List<ProductCreateResponse> createProducts(List<ProductCreateRequest> productCreateRequests);

    // Boolean checkProduct(List<String> productCodes, List<Integer> productQuantities);
    
}