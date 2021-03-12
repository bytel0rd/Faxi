package com.avenuer.faxi.wallets.enums;

public enum TransactionType {

    Funding("FUND"),
    Withdrawal("WITHDRAW");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
