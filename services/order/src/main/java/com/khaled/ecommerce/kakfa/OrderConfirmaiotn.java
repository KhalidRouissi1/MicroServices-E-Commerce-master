package com.khaled.ecommerce.kakfa;

import com.khaled.ecommerce.customer.CustomerResponse;
import com.khaled.ecommerce.order.PaymentMethod;
import com.khaled.ecommerce.prodcut.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmaiotn(
        String orderRefrence,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
        ) {
}
