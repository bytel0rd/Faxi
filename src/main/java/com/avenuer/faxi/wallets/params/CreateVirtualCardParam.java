package com.avenuer.faxi.wallets.params;

import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVirtualCardParam {

    private UUID ownerId;

    private PaymentGateway provider;

    private String billingName;
    private Currency currency;

    private String billingAddress;
    private String billingCity;
    private String state;
    private String country;

}
