package com.avenuer.faxi.thirdparties.flutterwave.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlutterWaveCardFund {
    private int amount;

    @JsonProperty("debit_currency")
    private String debitCurrency;
}
