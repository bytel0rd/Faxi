package com.avenuer.faxi.thirdparties.flutterwave.services;

import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterNubanAccount;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWareNubanReq;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlutterWaveNubanServiceTest {

    @Autowired
    private FlutterWaveNubanService nuban;

    private final Faker faker = new Faker();

    @Test
    public void createNuban() {

        String accountReference = String
                .format("BLFD-NUBAN-FW-%s", Utilities.randomString(8));

        FlutterWareNubanReq nubanReq = FlutterWareNubanReq.builder()
                .accountHolder("Micheal Billford")
                .email("micheal_bifford@billford.africa")
                .amountOfPaymentToReceive(5)
                .amountToBeCollected(5_000_000)
                .reference(accountReference)
                .isPermanent(false)
                .build();

        FlutterNubanAccount account = nuban.create(nubanReq);

        System.out.println(Utilities.objectToJSON(account));
        assertThat(account).isNotNull();

    }

    @Test
    public void retrieveNuban() {
        String orderReference = "URF_1613307965981_7000435";
        FlutterNubanAccount account = nuban.retrieve(orderReference);

        System.out.println(Utilities.objectToJSON(account));
        assertThat(account).isNotNull();

    }

}
