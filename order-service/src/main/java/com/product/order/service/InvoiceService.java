package com.product.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.order.entity.Invoice;
import com.product.order.entity.Order;
import com.product.order.entity.OrderItem;
import com.product.order.repository.InvoiceRepository;
import com.product.order.repository.OrderRepository;

import java.util.Date;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice generateInvoice(Integer orderId) {
        // Retrieve the order by its ID from the repository
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));

        // Create a new Invoice instance
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderId); // Set the order ID
        invoice.setDate(new Date()); // Set the current date as the invoice date
        invoice.setOrderItems(order.getOrderItems()); // Set the order items from the retrieved order
        invoice.setTotalAmount(calculateTotalAmount(order.getOrderItems())); // Calculate the total amount

        // Save the invoice to the repository and return the saved instance
        return invoiceRepository.save(invoice);
    }

    private double calculateTotalAmount(List<OrderItem> orderItems) {
        // Calculate the total amount by summing the prices of all order items
        return orderItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
}
