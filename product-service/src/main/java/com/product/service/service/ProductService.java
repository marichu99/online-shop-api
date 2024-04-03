package com.product.service.service;

import java.util.List;

import com.product.service.model.ProductCreateRequest;
import com.product.service.model.ProductCreateResponse;

public interface ProductService {

    ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest);

    List<ProductCreateResponse> findAll();

    ProductCreateResponse findById(Integer productId);

    List<ProductCreateResponse> createProducts(List<ProductCreateRequest> productCreateRequests);
    ProductCreateResponse updateProduct(Integer productId, ProductCreateRequest updatedProductRequest);

     // Define the deleteProduct method
     void deleteProduct(Integer productId);
}