package com.khaled.ecommerce.Email;

import lombok.Getter;

public enum EmailTemplates {
    PAYMENT_CONFIRMATION("payment-confiramtion.html","Payment Successfully processed"),
    ORDER_CONFIRMATION("order-confiramtion.html","order Successfully processed")
    ;
    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}
