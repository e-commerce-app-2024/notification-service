package com.ecommerce.app.kafka;

import com.ecommerce.app.dto.order.OrderConfirmation;
import com.ecommerce.app.dto.payment.PaymentConfirmation;
import com.ecommerce.app.service.EmailService;
import com.ecommerce.app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService notificationService;
    private final EmailService emailService;

    @KafkaListener(topics = "order", groupId = "order")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) {
        log.info("consume the message from order-topic :: {}", orderConfirmation);
        notificationService.saveOrderConfirmation(orderConfirmation);
        emailService.sendOrderConfirmationEmail(orderConfirmation);
    }


    @KafkaListener(topics = "payment", groupId = "payment")
    public void consumePaymentNotification(PaymentConfirmation paymentConfirmation) {
        log.info("consume the message from payment-topic :: {}", paymentConfirmation);
        notificationService.savePaymentConfirmation(paymentConfirmation);
        emailService.sendPaymentEmail(paymentConfirmation);
    }
}
