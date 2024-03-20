package com.product.inventoryservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.product.inventoryservice.model.GenericResponse;


@RestControllerAdvice
public class ControllerAdvise {

    @ExceptionHandler(NotEnoughQuantityException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public GenericResponse<?> handleProductNotFound(NotEnoughQuantityException ex){
            GenericResponse<?> resp = GenericResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(ex.getUnavailableItems())
                .build();
                return resp;
    }

}