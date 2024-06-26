package com.product.service.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCreateResponse {
    private Integer id;
    private String name;
    private BigDecimal price;
    private String category;
    private Integer quantity;
    private String imageUrl;
}