package com.product.order.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.order.model.GenericResponse;
import com.product.order.model.OrderRequest;
import com.product.order.service.OrderService;

@RequestMapping("api/orders")
@CrossOrigin(origins = "http://localhost:1841/")
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("placeOrder")
    
    public GenericResponse<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        System.out.println("The request is +++++++"+orderRequest);
        orderService.placeOrder(orderRequest);

        GenericResponse<String> resp = GenericResponse.<String>builder()
                .success(true)
                .msg("Order placed successfully")
                .build();
        return resp;
    }

}