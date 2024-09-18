package com.khaled.ecommerce.notification;

import com.khaled.ecommerce.kafka.order.OrderConfirmation;
import com.khaled.ecommerce.kafka.payment.PaymentConfirmation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Document
public class Notification {
    @Id
    private String id;
    private Notificationtype notificationtype;
    private LocalDateTime notificationdate;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
