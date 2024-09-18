package com.khaled.ecommerce.kafka;

import com.khaled.ecommerce.customer.CustomerResponse;
import com.khaled.ecommerce.order.PaymentMethod;
import com.khaled.ecommerce.prodcut.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderRefrence,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
        ) {
}
