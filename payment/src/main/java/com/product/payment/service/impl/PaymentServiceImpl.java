package com.product.payment.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import com.product.payment.entity.Payment;
import com.product.payment.model.PaymentRequest;
import com.product.payment.model.PaymentResponse;
import com.product.payment.repository.ProductRepository;
import com.product.payment.service.PaymentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final ProductRepository productRepository;

    public PaymentServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public PaymentResponse makePayment(Payment payment) {
        var madePayment = productRepository.save(payment);
        return mapToPaymentResponse(madePayment);
    }

    @Override
    public List<PaymentResponse> getAllPayments() {

        return productRepository.findAll().stream().map(this::mapToPaymentResponse).toList();
    }

    @Override
    public PaymentResponse findPaymentById(int id) {
        var payment =  productRepository.findById(id);
        if (payment.isPresent()) {
            return mapToPaymentResponse(payment.get());
        }else{
            log.info("Payment has not been found");
        }
        return null;
    }

    public PaymentResponse mapToPaymentResponse(Payment source) {
        PaymentResponse payment = new PaymentResponse();
        BeanUtils.copyProperties(source, payment);
        return payment;
    }

    public Payment mapRequestToEntity(PaymentRequest request){
        Payment entity=new Payment();
        BeanUtils.copyProperties(request,entity);
        return entity;
    }

}
