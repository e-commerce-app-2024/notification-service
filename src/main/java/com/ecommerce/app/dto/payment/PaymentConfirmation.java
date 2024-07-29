package com.ecommerce.app.dto.payment;

import java.math.BigDecimal;


public record PaymentConfirmation(
        String orderReference,
        BigDecimal amount,
        PaymentMethodEnum PaymentMethod,
        String customerName,
        String customerEmail
) {
}
