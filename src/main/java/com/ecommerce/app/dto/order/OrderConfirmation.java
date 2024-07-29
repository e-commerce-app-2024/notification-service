package com.ecommerce.app.dto.order;

import com.ecommerce.app.dto.payment.PaymentMethodEnum;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethodEnum PaymentMethod,
        Customer customer,
        List<Product> products
) {
}
