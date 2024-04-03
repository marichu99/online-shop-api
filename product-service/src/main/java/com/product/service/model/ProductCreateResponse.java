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
    private String name;
    private BigDecimal price;
    private String category;
    private Integer quantity;
    private String imageUrl;

      private Integer id;
    // Other fields and getter methods...

    public void setId(Integer id) {
        this.id = id;
    }
}