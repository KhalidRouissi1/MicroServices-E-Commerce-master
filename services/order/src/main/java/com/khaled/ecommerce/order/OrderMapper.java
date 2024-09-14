package com.khaled.ecommerce.order;

import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .reference(orderRequest.refrence())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .build();
        return order;
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
