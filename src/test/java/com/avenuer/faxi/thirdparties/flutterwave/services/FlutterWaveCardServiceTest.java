package com.avenuer.faxi.thirdparties.flutterwave.services;

import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.thirdparties.flutterwave.enums.FlutterWardCardStatus;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCardFund;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCardRequest;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCardWithdraw;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveCreatedCard;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlutterWaveCardServiceTest {

    @Autowired
    private FlutterWaveCardService cardSvc;

    private final Faker faker = new Faker();

    @Test
    public void createCard() {

        FlutterWaveCardRequest cardRequest = FlutterWaveCardRequest.builder()
                .currency("NGN")
                .amountToFund(1000)
                .billingName(faker.name().fullName())
                .billingAddress(faker.address().streetAddress())
                .billingCity(faker.address().city())
                .state(faker.address().stateAbbr())
                .country(faker.address().countryCode())
                .callBackURL("http://staging-api.billford.africa/api/v1/cards/charge")
                .build();

        FlutterWaveCreatedCard card = cardSvc.create(cardRequest);

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();

    }

    @Test
    public void getCard() {
        String cardId = "";

        FlutterWaveCreatedCard card = cardSvc.retrieve(cardId);

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();

    }

    @Test
    public void fundCard() {
        String cardId = "";

        FlutterWaveCardFund fund = FlutterWaveCardFund.builder()
                .amount(100)
                .debitCurrency("NGN")
                .build();

        boolean isDone = cardSvc.fund(cardId, fund);

        System.out.println(String.format("Funding Status is %s", isDone));

        assertThat(isDone).isTrue();

    }

    @Test
    public void withdrawFromCard() {

        String cardId = "";

        FlutterWaveCardWithdraw withdrawal = FlutterWaveCardWithdraw.builder()
                .amount(100)
                .build();

        boolean isDone = cardSvc.withdrawFrom(cardId, withdrawal);

        System.out.println(String.format("Withdrawal Status is %s", isDone));

        assertThat(isDone).isTrue();

    }

    @Test
    public void changeCardStatus() {

        String cardId = "";

        boolean isDone = cardSvc.setCardStatus(cardId, FlutterWardCardStatus.BLOCK);

        System.out.println(String.format("Status change is %s", isDone));

        assertThat(isDone).isTrue();
    }

    @Test
    public void terminateCard() {

        String cardId = "";

        boolean isDone = cardSvc.terminateCard(cardId);

        System.out.println(String.format("Termination Status is %s", isDone));

        assertThat(isDone).isTrue();
    }


}
