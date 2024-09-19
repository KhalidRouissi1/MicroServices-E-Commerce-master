package com.khaled.ecommerce.kafka;

import com.khaled.ecommerce.Email.EmailService;
import com.khaled.ecommerce.kafka.order.OrderConfirmation;
import com.khaled.ecommerce.kafka.payment.PaymentConfirmation;
import com.khaled.ecommerce.notification.Notification;
import com.khaled.ecommerce.notification.NotificationRepository;
import com.khaled.ecommerce.notification.Notificationtype;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotficationConsumer
{
    private final NotificationRepository repository;
    private final EmailService emailService;
    @KafkaListener(topics ="payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming the message from payment topic :: {}", paymentConfirmation);
        repository.save(
                Notification.builder()
                        .notificationtype(Notificationtype.PAYMENT_CONFIRMATION)
                        .notificationdate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );
        var customerName = paymentConfirmation.customerFirstName()+" "+ paymentConfirmation.customerLastName();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );

    }

    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming the message from order topic :: {}", orderConfirmation);
        repository.save(
                Notification.builder()
                        .notificationtype(Notificationtype.ORDER_CONFIRMATION)
                        .notificationdate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );
        var customerName = orderConfirmation.customer().firstName() + " "+ orderConfirmation.customer().lastName();
        emailService.sendOrderSuccessEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderRefrence(),
                orderConfirmation.products()
        );

    }
}
