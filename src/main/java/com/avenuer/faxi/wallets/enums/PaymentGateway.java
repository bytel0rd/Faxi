package com.avenuer.faxi.wallets.enums;

public enum PaymentGateway {
    FLUTTERWAVE("FLTRW"),
    PAYSTACK("PYSTK");

    private final String value;

    PaymentGateway(String shortcode) {
        value = shortcode;
    }

    @Override
    public String toString() {
        return value;
    }
}
