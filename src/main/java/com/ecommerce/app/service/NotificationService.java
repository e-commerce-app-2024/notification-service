package com.ecommerce.app.service;

import com.ecommerce.app.dto.order.OrderConfirmation;
import com.ecommerce.app.dto.payment.PaymentConfirmation;
import com.ecommerce.app.enums.NotificationTypeEnum;
import com.ecommerce.app.model.Notification;
import com.ecommerce.app.repo.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepo notificationRepo;

    public void saveOrderConfirmation(OrderConfirmation orderConfirmation) {
        Notification notification = Notification.builder()
                .type(NotificationTypeEnum.ORDER_CONFIRMATION)
                .createAt(LocalDateTime.now())
                .orderConfirmation(orderConfirmation)
                .build();
        notificationRepo.save(notification);
    }

    public void savePaymentConfirmation(PaymentConfirmation paymentConfirmation) {
        Notification notification = Notification.builder()
                .type(NotificationTypeEnum.PAYMENT_CONFIRMATION)
                .createAt(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation)
                .build();
        notificationRepo.save(notification);
    }


}
