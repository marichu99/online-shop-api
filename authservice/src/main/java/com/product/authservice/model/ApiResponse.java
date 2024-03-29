package com.product.authservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ApiResponse<T> {
    private String message;
    private boolean success;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    
}
