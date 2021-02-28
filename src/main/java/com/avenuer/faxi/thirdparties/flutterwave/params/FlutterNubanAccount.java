package com.avenuer.faxi.thirdparties.flutterwave.params;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlutterNubanAccount {

    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("flw_ref")
    private String flwRef;
    @JsonProperty("order_ref")
    private String orderRef;

    private String frequency;

    private Integer amount;

    @JsonProperty("expiry_date")
    private Date expiryDate;
    @JsonProperty("created_at")
    private Date createdAt;

    private String note;
    @JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("response_message")
    private String responseMessage;

}
