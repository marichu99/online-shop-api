package com.product.order.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.product.order.entity.Order;
import com.product.order.entity.OrderItem;
import com.product.order.exception.InventoryServiceException;
import com.product.order.exception.NotEnoughQuantityException;
import com.product.order.exception.OrderServiceException;
import com.product.order.model.GenericResponse;
import com.product.order.model.OrderItemRequest;
import com.product.order.model.OrderRequest;
import com.product.order.repository.OrderRepository;
import com.product.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
        this.webClientBuilder = webClientBuilder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();

        List<String> productCodes = new ArrayList<>();
        List<Integer> productQuantities = new ArrayList<>();

        System.out.println("The order request is ++++++" + orderRequest);

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            System.out.println("The order Item request is ++++++" + orderItemRequest);
            productCodes.add(orderItemRequest.getProductCode());
            productQuantities.add(orderItemRequest.getQuantity());
        }
        log.info("The orderequest is", orderRequest);
        log.info("productCodes", productCodes);
        System.out.println("The product codes are ......" + productCodes);
        log.info("productQuantities", productQuantities);

        GenericResponse<?> response = webClientBuilder.build().post()
                .uri("http://localhost:6060/api/products/addToCart")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productCodes))
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> handleError(clientResponse))
                .bodyToMono(new ParameterizedTypeReference<GenericResponse<?>>() {
                })
                .block();

        log.info("The response is " + response);
        if (response.isSuccess()) {
            // stock
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderTime(Instant.now());
            var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
            order.setOrderItems(orderItems);
            orderRepository.save(order);
        } else {
            log.error("Not Enough stock");
            log.info("{}", response.getData());
            if (response.getData() instanceof Map) {
                throw new NotEnoughQuantityException(response.getMsg(), (Map<String, Integer>) response.getData());
            }
            throw new OrderServiceException(response.getMsg());
        }
        return order.getOrderNumber();

    }

    private Mono<? extends Throwable> handleError(ClientResponse response) {
        log.error("Client error received: {}", response.statusCode());
        return Mono.error(new InventoryServiceException("Error in inventory service"));
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }
}
