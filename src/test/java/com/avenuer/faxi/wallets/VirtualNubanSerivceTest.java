package com.avenuer.faxi.wallets;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import com.avenuer.faxi.wallets.models.VirtualNuban;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import com.avenuer.faxi.wallets.services.VirtualNubanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class VirtualNubanSerivceTest {

    @Autowired
    private VirtualNubanService virtualNuban;

    private  AuthProfile auth = AuthProfile.builder()
            .firstName("Emeka")
            .lastName("Test")
            .role(ROLE.ADMIN)
            .build();

    @Test
    public void create() {

        CreateNubanParam req = CreateNubanParam.builder()
                .accountName("Emeka Micheal")
                .email("emeka_micheal_001@billford.africa")
                .currency(Currency.USD)
                .nubanProvider(PaymentGateway.FLUTTERWAVE)
                .ownerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"))
                .build();

        VirtualNuban nuban = virtualNuban.create(auth, req);

        System.out.println(Utilities.objectToJSON(nuban));

        assertThat(nuban).isNotNull();

    }

    @Test
    public void retrieve() {

        VirtualNuban nuban = virtualNuban.retrieve(UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        System.out.println(Utilities.objectToJSON(nuban));

        assertThat(nuban).isNotNull();
    }

    @Test
    public void retrieveNubanWithUserId() {
        VirtualNuban nuban = virtualNuban.retrieveNubanWithUserId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"));

        System.out.println(Utilities.objectToJSON(nuban));

        assertThat(nuban).isNotNull();
    }

    @Test
    @SneakyThrows
    public void updateNuban() {

        VirtualNuban nuban = virtualNuban.retrieve(UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        nuban.setIsActive(true);

        VirtualNuban updatedNuban = virtualNuban.update(auth, nuban);

        System.out.println(Utilities.objectToJSON(updatedNuban));

        assertThat(updatedNuban).isNotNull();

    }

    @Test
    public void deactivateNuban() {
        VirtualNuban deactivatedNuban = virtualNuban.deactivate(auth, UUID.fromString("45133429-3ce2-4290-8144-91f2cc8935d1"));

        System.out.println(Utilities.objectToJSON(deactivatedNuban));

        assertThat(deactivatedNuban).isNotNull();
    }

}
