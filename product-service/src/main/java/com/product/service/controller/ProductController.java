package com.product.service.controller;


import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.service.exception.ProductNotFoundException;
import com.product.service.model.GenericResponse;
import com.product.service.model.ProductCreateRequest;
import com.product.service.model.ProductCreateResponse;
import com.product.service.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins =  "http://localhost:1841/") 
@RequestMapping("api/products")
@Slf4j
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public GenericResponse<List<ProductCreateResponse>> list() {
       List<ProductCreateResponse> pr = productService.findAll();
       GenericResponse<List<ProductCreateResponse>> resp = GenericResponse.<List<ProductCreateResponse>>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }

    @PutMapping("/{productId}")
    public GenericResponse<ProductCreateResponse> updateProduct(
            @PathVariable(name = "productId") Integer productId,
            @RequestBody ProductCreateRequest updatedProductRequest) {
        log.info("Updating product with ID: {}", productId);
        
        try {
            ProductCreateResponse updatedProduct = productService.updateProduct(productId, updatedProductRequest);
            return GenericResponse.<ProductCreateResponse>builder()
                    .success(true)
                    .msg("Product updated successfully")
                    .data(updatedProduct) // Include the updated product details in the response
                    .build();
        } catch (Exception e) {
            log.error("Error updating product with ID: {}", productId, e);
            return GenericResponse.<ProductCreateResponse>builder()
                    .success(false)
                    .msg("Failed to update product")
                    .build();
        }
    }
    



    @GetMapping("/all")
public GenericResponse<List<ProductCreateResponse>> getAllProducts() {
    List<ProductCreateResponse> products = productService.findAll();
    return GenericResponse.<List<ProductCreateResponse>>builder()
            .success(true)
            .msg("All products fetched successfully")
            .data(products)
            .build();
}


    @GetMapping("/{productId}")
    public GenericResponse<ProductCreateResponse> findById(@PathVariable(name = "productId")  Integer productId) {
      ProductCreateResponse pr = (ProductCreateResponse) productService.findById(productId);
       GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Data fetched Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
                return resp;
    }
    @PostMapping
    public GenericResponse<ProductCreateResponse> createProduct(
            @RequestBody ProductCreateRequest productCreateRequest) {
                log.info("We received : {}",productCreateRequest);
        ProductCreateResponse pr = productService.createProduct(productCreateRequest);
        GenericResponse<ProductCreateResponse> resp = GenericResponse.<ProductCreateResponse>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(pr)
                .build();
                log.info("We returned : {}",pr);
        return resp;
    }
    @PostMapping("/seed")
    public GenericResponse<List<ProductCreateResponse>> createProducts(
            @RequestBody List<ProductCreateRequest> productCreateRequests) {
        // Log the received requests
        log.info("Received {} product creation requests", productCreateRequests.size());
        
        // Process each request and create products
        List<ProductCreateResponse> responses = productService.createProducts(productCreateRequests);
        
        // Log the responses
        log.info("Returned {} product creation responses", responses.size());
        
        // Build and return the response
        GenericResponse<List<ProductCreateResponse>> resp = GenericResponse.<List<ProductCreateResponse>>builder()
                .success(true)
                .msg("Data saved Successfully")
                .data(responses)
                .build();
        return resp;
    }
    @DeleteMapping("/{productId}")
public GenericResponse<Void> deleteProduct(@PathVariable Integer productId) {
    log.info("Deleting product with ID: {}", productId);
    
    try {
        productService.deleteProduct(productId);
        return GenericResponse.<Void>builder()
                .success(true)
                .msg("Product deleted successfully")
                .build();
    } catch (ProductNotFoundException e) {
        log.error("Product with ID {} not found", productId);
        return GenericResponse.<Void>builder()
                .success(false)
                .msg("Product not found")
                .build();
    } catch (Exception e) {
        log.error("Error deleting product with ID: {}", productId, e);
        return GenericResponse.<Void>builder()
                .success(false)
                .msg("Failed to delete product")
                .build();
    }
}


}