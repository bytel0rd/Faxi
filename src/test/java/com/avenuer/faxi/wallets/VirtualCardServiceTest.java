package com.avenuer.faxi.wallets;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import com.avenuer.faxi.wallets.models.VirtualCard;
import com.avenuer.faxi.wallets.params.CreateVirtualCardParam;
import com.avenuer.faxi.wallets.services.VirtualCardService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VirtualCardServiceTest {

    @Autowired
    private VirtualCardService virtualCard;

    private AuthProfile auth = AuthProfile.builder()
            .firstName("Emeka")
            .lastName("Test")
            .role(ROLE.ADMIN)
            .build();


    @Test
    public void create() {

        CreateVirtualCardParam req = CreateVirtualCardParam.builder()
                .provider(PaymentGateway.FLUTTERWAVE)
                .ownerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"))
                .billingName("Emeka Micheal")
                .currency(Currency.NGN)
                .billingAddress("Aloba Estate, Orogun")
                .billingCity("Ibadan")
                .state("OYO")
                .country("Nigeria")
                .build();

        VirtualCard card = virtualCard.create(auth, req);

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();

    }

    @Test
    public void retrieve() {

        VirtualCard card = virtualCard.retrieve(UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();
    }

    @Test
    public void retrieveCardWithOwnerId() {
        VirtualCard card = virtualCard.retrieveCardWithOwnerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"));

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();
    }

    @Test
    @SneakyThrows
    public void updateVirtualCard() {

        VirtualCard card = virtualCard.retrieve(UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        card.setIsActive(true);

        VirtualCard updatedCard = virtualCard.update(auth, card);

        System.out.println(Utilities.objectToJSON(updatedCard));

        assertThat(updatedCard).isNotNull();

    }

    @Test
    public void deactivateCard() {
        VirtualCard card = virtualCard.deactivate(auth, UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        System.out.println(Utilities.objectToJSON(card));

        assertThat(card).isNotNull();
    }


}
