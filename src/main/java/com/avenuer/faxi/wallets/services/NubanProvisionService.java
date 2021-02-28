package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWareNubanReq;
import com.avenuer.faxi.thirdparties.flutterwave.services.FlutterWaveNubanService;
import com.avenuer.faxi.wallets.dao.VirtualNubanDao;
import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import com.avenuer.faxi.wallets.models.VirtualNuban;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NubanProvisionService {

    @Autowired
    private FlutterWaveNubanService flutterWaveNuban;

    @Value("${faxi.nuban.initalFundingAmount}")
    private Integer initalAmountToFund;

    public VirtualNuban create(CreateNubanParam nubanReq) {

        switch (nubanReq.getNubanProvider()) {
            case FLUTTERWAVE :
                return provisionFlutterWaveNuban(nubanReq);
            default:
                throw new RuntimeException("Nuban Provider Selected is not available, please select another provider");
        }

    }

    private VirtualNuban provisionFlutterWaveNuban(CreateNubanParam nubanReq) {

        String accountReference = createNubanReference(nubanReq.getNubanProvider());

        FlutterWareNubanReq flutterwaveReq = FlutterWareNubanReq.builder()
                .reference(accountReference)
                .amountOfPaymentToReceive(initalAmountToFund)
                .isPermanent(true)
                .email(nubanReq.getEmail())
                .accountHolder(nubanReq.getAccountName())
                .amountToBeCollected(5_000_000 * 100)
                .build();

        var createdNuban = flutterWaveNuban.create(flutterwaveReq);

        var nuban = VirtualNuban.builder()
                .accountNumber(createdNuban.getAccountNumber())
                .accountReference(accountReference)
                .bankName(createdNuban.getBankName())
                .currency(Currency.NGN)
                .isActive(true)
                .issuer(nubanReq.getNubanProvider())
                .issuerReference(createdNuban.getOrderRef())
                .build();

        return nuban;
    }


    private String createNubanReference(PaymentGateway provider) {
        return String.format("BLFD-NUBAN-%s-%s", provider,  Utilities.randomString(8));
    }
}
