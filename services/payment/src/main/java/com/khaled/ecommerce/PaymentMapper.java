package com.khaled.ecommerce;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentMapper {
    public Payment toPaymentMethod(@Valid PaymentRequest request) {
        Payment payment = new Payment();
        payment.setId(request.id());
        payment.setOrderId(request.orderId());
        payment.setPaymentMethod(request.paymentMethod());
        payment.setAmount(request.amount());
        payment.setId(request.id());
        payment.setCreatedDate(LocalDateTime.now());
        payment.setLastModifiedDate(LocalDateTime.now());
        return payment;
    }
}
