package com.product.inventoryservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse<T> {

    private String message;
    private boolean success;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;

    
}
