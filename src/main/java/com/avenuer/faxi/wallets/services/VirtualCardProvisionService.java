package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCardRequest;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCreatedCard;
import com.avenuer.faxi.thirdparties.flutterwave.services.FlutterWaveCardService;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import com.avenuer.faxi.wallets.models.VirtualCard;
import com.avenuer.faxi.wallets.params.CreateVirtualCardParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VirtualCardProvisionService {

    @Value("${flutterwave.card.callbackURL}")
    private String flutterWaveCallbackURL;

    @Autowired
    private FlutterWaveCardService flutterwave;

    public VirtualCard create(CreateVirtualCardParam cardRequest) {
        switch (cardRequest.getProvider()) {
            case FLUTTERWAVE:
            case PAYSTACK:
                return provideFlutterWaveCard(cardRequest);
            default:
                throw new RuntimeException("Provider does not provide virtual cards, please choose another card provider");
        }
    }

    private VirtualCard provideFlutterWaveCard(CreateVirtualCardParam cardRequest) {

        FlutterWaveCardRequest request = FlutterWaveCardRequest.builder()
                .amountToFund(0)
                .currency(cardRequest.getCurrency().name())
                .billingAddress(cardRequest.getBillingAddress())
                .billingCity(cardRequest.getBillingCity())
                .billingName(cardRequest.getBillingName())
                .country(cardRequest.getCountry())
                .state(cardRequest.getState())
                .callBackURL(flutterWaveCallbackURL)
                .build();

        FlutterWaveCreatedCard card = flutterwave.create(request);

        VirtualCard virtualCard = VirtualCard.builder()
                .ownerId(cardRequest.getOwnerId())

                .provider(PaymentGateway.FLUTTERWAVE)
                .accountID(card.getAccountID())
                .isActive(card.getIsActive())
                .cardType(card.getCardType())

                .nameOnCard(card.getNameOnCard())
                .amount(card.getAmount())
                .currency(card.getCurrency())
                .cvv(card.getCvv())
                .expiration(card.getExpiration())

                .cardHash(card.getCardHash())
                .cardPan(card.getCardPan())
                .maskedPan(card.getMaskedPan())

                .address1(card.getAddress1())
                .zipCode(card.getZipCode())
                .city(card.getCity())
                .state(card.getState())
                .build();

        return virtualCard;
    }

}
