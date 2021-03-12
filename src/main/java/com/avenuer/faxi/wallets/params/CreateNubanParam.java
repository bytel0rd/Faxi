package com.avenuer.faxi.wallets.params;

import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateNubanParam {
    private String accountName;
    private PaymentGateway nubanProvider;
    private Currency currency;
    private String email;
    private UUID ownerId;
}
