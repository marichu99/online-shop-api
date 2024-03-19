package com.product.order.service;

import com.product.order.model.OrderRequest;

public interface OrderService {

    void placeOrder(OrderRequest orderRequest);
    
}
