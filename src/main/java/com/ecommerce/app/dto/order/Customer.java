package com.ecommerce.app.dto.order;

import lombok.Builder;

@Builder
public record Customer(
        String id,
        String firstName,
        String lastName,
        String email
) {
}
