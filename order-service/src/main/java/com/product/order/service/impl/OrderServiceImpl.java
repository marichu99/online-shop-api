package com.product.order.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.product.order.entity.Order;
import com.product.order.entity.OrderItem;
import com.product.order.model.OrderItemRequest;
import com.product.order.model.OrderRequest;
import com.product.order.repository.OrderRepository;
import com.product.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setOrderTime(Instant.now());
        List<OrderItem> orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }
}
