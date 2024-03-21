package com.product.order.service;

import com.product.order.model.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);
    
}
