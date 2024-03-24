package com.product.payment.service;

import java.util.List;

import com.product.payment.entity.Payment;
import com.product.payment.model.PaymentResponse;

public interface PaymentService {
    PaymentResponse  makePayment(Payment payment);

    List<PaymentResponse>  getAllPayments();

    PaymentResponse findPaymentById(int id);
}
