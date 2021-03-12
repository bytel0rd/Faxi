package com.avenuer.faxi.thirdparties.flutterwave.enums;

public enum FlutterWardCardStatus {
    BLOCK("block"),
    UN_BLOCK("unblock");

    private final String value;

    FlutterWardCardStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
