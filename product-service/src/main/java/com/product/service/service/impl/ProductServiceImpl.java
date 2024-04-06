package com.product.service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.service.entity.Product;
import com.product.service.exception.ProductNotFoundException;
import com.product.service.model.ProductCreateRequest;
import com.product.service.model.ProductCreateResponse;
import com.product.service.repository.ProductRepository;
import com.product.service.service.ProductService;

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

    @SuppressWarnings("null")
    private ProductCreateResponse mapToProductCreateResponse(Product source) {
        ProductCreateResponse target = new ProductCreateResponse();
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
    public void addToCart(List<Integer> productList) {
         productRepository.updateCartStatusToCarted(productList);
    }

    // public Boolean checkProduct(List<String> productCodes, List<Integer> productQuantities) {
    //     Map<String, Integer> unavailableItems = new HashMap<>();

    //     for (int i = 0; i < productCodes.size(); i++) {
    //         String productCode = productCodes.get(i);
    //         Integer productQuantity = productQuantities.get(i);
    //         Product product = productRepository.findByProductCode(productCode).orElse(null);
    //         if (product != null) {
    //             // check if enough
    //             var dbInventory = product.getQuantity();
    //             if (productQuantity > dbInventory) {
    //                 unavailableItems.put(productCode, productQuantity - dbInventory);
    //             }
    //         } else {
    //             unavailableItems.put(productCode, productQuantity);
    //         }
    //     }
    //     if (unavailableItems.isEmpty()) {
    //         return true;
    //     } else {
    //         throw new RuntimeException("Not Enough Quantity in Stock");
    //     }
    // }

}