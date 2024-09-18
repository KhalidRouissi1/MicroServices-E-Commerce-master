package com.khaled.ecommerce.payment;

import com.khaled.ecommerce.customer.CustomerResponse;
import com.khaled.ecommerce.order.PaymentMethod;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
