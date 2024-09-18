package com.khaled.ecommerce;

import com.khaled.ecommerce.notification.NotificationProducer;
import com.khaled.ecommerce.notification.PaymentNotificationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {
        private final PaymentRepository repository;
        private final PaymentMapper mapper;
        private final NotificationProducer notificationProducer;
        public Integer createPayment(@Valid PaymentRequest request) {

        var payment = repository.save(mapper.toPaymentMethod(request));
       /*notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                        )
        );*/
        return payment.getId();

    }
}
