package com.ecommerce.app.model;


import com.ecommerce.app.dto.order.OrderConfirmation;
import com.ecommerce.app.dto.payment.PaymentConfirmation;
import com.ecommerce.app.enums.NotificationTypeEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Document(collection = "notification")
public class Notification {

    @Id
    private String id;
    private NotificationTypeEnum type;
    private LocalDateTime createAt;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
