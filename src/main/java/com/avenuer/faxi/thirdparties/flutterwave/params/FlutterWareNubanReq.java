package com.avenuer.faxi.thirdparties.flutterwave.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlutterWareNubanReq {

    @JsonProperty("narration")
    private String accountHolder;
    private String email;

    @JsonProperty("amount")
    private int amountToBeCollected;
    @JsonProperty("frequency")
    private int amountOfPaymentToReceive;

    @JsonProperty("is_permanent")
    private boolean isPermanent;

    @JsonProperty("tx_ref")
    private String reference;

}
