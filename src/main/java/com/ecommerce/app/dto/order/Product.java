package com.ecommerce.app.dto.order;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record Product(
        Long id,
        String name,
        BigDecimal price,
        Long quantity,
        BigDecimal totalPrice
) {
}
