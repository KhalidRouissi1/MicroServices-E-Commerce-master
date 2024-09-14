package com.khaled.ecommerce.kakfa;

import com.khaled.ecommerce.order.Order;
import com.khaled.ecommerce.order.OrderController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final KafkaTemplate<String, OrderConfirmaiotn>  kafkaTemplate;

    public void sendOrederConfirmation(OrderConfirmaiotn orderConfirmaiotn){

        log.info("Sending order confirmation");
        Message<OrderConfirmaiotn> message = MessageBuilder
                .withPayload(orderConfirmaiotn)
                .setHeader(KafkaHeaders.TOPIC,"order-topic")
                .build();
        kafkaTemplate.send(message);

    }

}
