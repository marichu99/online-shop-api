package com.product.order.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.Gson;
import com.product.order.entity.Order;
import com.product.order.entity.OrderItem;
import com.product.order.exception.InventoryServiceException;
import com.product.order.model.OrderItemRequest;
import com.product.order.model.OrderRequest;
import com.product.order.repository.OrderRepository;
import com.product.order.service.OrderService;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository, WebClient.Builder webClientBuilder) {
        this.orderRepository = orderRepository;
    }

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

        // Convert productCodes to a map with key "productCodes"
        Map<String, List<String>> requestBodyMap = Collections.singletonMap("productCodes", productCodes);

        // Convert the map to JSON format
        String jsonRequestBody = new Gson().toJson(requestBodyMap);

        // Set up OkHttpClient
        OkHttpClient client = new OkHttpClient();

        // Create request body with JSON content type
        RequestBody requestBody = RequestBody.create(jsonRequestBody, MediaType.parse("application/json"));

        // Create POST request
        Request request = new Request.Builder()
                .url("http://localhost:6060/api/products/addToCart")
                .post(requestBody)
                .build();

        try {
            // Execute the request
            Response response = client.newCall(request).execute();
            log.info("The response is " + response);
            if (response.isSuccessful()) {
                // stock
                order.setOrderNumber(UUID.randomUUID().toString());
                order.setOrderTime(Instant.now());
                var orderItems = orderRequest.getOrderItems().stream().map(this::mapToOrderItemEntity).toList();
                order.setOrderItems(orderItems);
                orderRepository.save(order);
            
            } else {
                // Handle error response
                handleError(response);
            }
        } catch (IOException e) {
            // Handle IO Exception
            e.printStackTrace();
        }

        return order.getOrderNumber();

    }

    private Mono<? extends Throwable> handleError(Response response) {
        log.error("Client error received: {}", response.code());
        return Mono.error(new InventoryServiceException("Error in inventory service"));
    }

    private OrderItem mapToOrderItemEntity(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(itemRequest, orderItem);
        return orderItem;
    }
}
