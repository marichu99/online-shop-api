package com.product.service.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.product.service.entity.Product;
import com.product.service.exception.ProductNotFoundException;
import com.product.service.model.GenericResponse;
import com.product.service.model.ProductCreateRequest;
import com.product.service.model.ProductCreateResponse;
import com.product.service.repository.ProductRepository;
import com.product.service.service.ProductService;

import jakarta.ws.rs.NotFoundException;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductCreateResponse createProduct(ProductCreateRequest productCreateRequest) {
        @SuppressWarnings("null")
        var savedProduct = productRepository.save(mapToProductEntity(productCreateRequest));
        return mapToProductCreateResponse(savedProduct);
    }

    @SuppressWarnings("null")
    private Product mapToProductEntity(ProductCreateRequest source) {
        Product target = new Product();
        BeanUtils.copyProperties(source, target);
        return target;

    }

   
    @Override
    public ProductCreateResponse findById(Integer productId) {
        var pr = productRepository.findById(productId);
        if (pr.isPresent()) {
            return mapToProductCreateResponse(pr.get());
        }
        throw new ProductNotFoundException("Product with id not found");
    }

    

    @Override
    public List<ProductCreateResponse> findAll() {
        return productRepository.findAll().stream().map(this::mapToProductCreateResponse).toList();
    }

    @Override
    public List<ProductCreateResponse> createProducts(List<ProductCreateRequest> productCreateRequests) {
        return productCreateRequests.stream().map(this::mapToProductEntity).map(productRepository::save)
                .map(this::mapToProductCreateResponse).collect(Collectors.toList());
    }
    @Override
    public ProductCreateResponse updateProduct(Integer productId, ProductCreateRequest updatedProductRequest) {
        // Fetch the existing product by productId
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Update the existing product with the data from updatedProductRequest
        existingProduct.setName(updatedProductRequest.getName());
        existingProduct.setPrice(updatedProductRequest.getPrice());
        existingProduct.setCategory(updatedProductRequest.getCategory());
        existingProduct.setImageUrl(updatedProductRequest.getImageUrl());
        existingProduct.setQuantity(updatedProductRequest.getQuantity());

        // Save the updated product
        Product updatedProduct = productRepository.save(existingProduct);

        // Convert the updated product to a response DTO and return
        return mapToProductCreateResponse(updatedProduct);
    }

    // Helper method to map Product entity to ProductCreateResponse DTO
    private ProductCreateResponse mapToProductCreateResponse(Product product) {
        return ProductCreateResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .quantity(product.getQuantity())
                .imageUrl(product.getImageUrl())
                .build();
    }

    @Override
public void deleteProduct(Integer productId) {
    // Fetch the existing product by productId
    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found"));

    // Delete the product
    productRepository.delete(existingProduct);
}




}