package com.avenuer.faxi.thirdparties.flutterwave.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlutterWaveCardRequest {

    private String currency;

    @JsonProperty("amount")
    private Integer amountToFund;

    @JsonProperty("billing_name")
    private String billingName;

    @JsonProperty("billing_address")
    private String billingAddress;

    @JsonProperty("billing_city")
    private String billingCity;

    @JsonProperty("billing_state")
    private String state;

    @JsonProperty("billing_country")
    private String country;

    @JsonProperty("callback_url")
    private String callBackURL;

}
