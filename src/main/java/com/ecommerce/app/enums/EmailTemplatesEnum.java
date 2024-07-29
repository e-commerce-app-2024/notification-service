package com.ecommerce.app.enums;

import lombok.Getter;

public enum EmailTemplatesEnum {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "payment success"),
    ORDER_CONFIRMATION("order-confirmation.html", "order confirmation");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplatesEnum(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
