package com.product.order.exception;

public class OrderServiceException extends RuntimeException{

    public OrderServiceException(String message){
        super(message);
    }

}