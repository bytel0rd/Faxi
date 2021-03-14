package com.avenuer.faxi.shared;

public enum EnvironmentMode {

    TEST("TEST"),
    LIVE("LIVE");

    String value;

    EnvironmentMode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
