package com.khaled.ecommerce.notification;

import com.khaled.ecommerce.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest (
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail


){}