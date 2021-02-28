package com.avenuer.faxi.thirdparties.flutterwave.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class FlutterWaveCreatedCard {
    private UUID id;
    private Long accountID;
    private String cardType;
    private String nameOnCard;
    private String amount;
    private String currency;
    private String cvv;
    private String expiration;

    @JsonProperty("card_hash")
    private UUID cardHash;
    @JsonProperty("card_pan")
    private String cardPan;
    @JsonProperty("masked_pan")
    private String maskedPan;

    private Object sendTo;
    private Object binCheckName;

    private String city;
    private String state;
    @JsonProperty("address_1")
    private String address1;
    @JsonProperty("address_2")
    private Object address2;
    @JsonProperty("zip_code")
    private String zipCode;

    private Date createdAt;
    private Boolean isActive;
    private String callbackURL;

}
