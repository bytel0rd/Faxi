package com.avenuer.faxi.merchants;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.merchants.models.Merchant;
import com.avenuer.faxi.merchants.params.CreateMerchantParam;
import com.avenuer.faxi.merchants.params.CreatedMerchant;
import com.avenuer.faxi.merchants.services.MerchantService;
import com.avenuer.faxi.shared.Utilities;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantServiceTest {

    @Autowired
    private MerchantService merchantSvc;

    private AuthProfile auth = AuthProfile.builder()
            .firstName("Emeka")
            .lastName("Test")
            .role(ROLE.ADMIN)
            .build();


    @Test
    public void create() {

        CreateMerchantParam req = CreateMerchantParam.builder()
                .name("BillFord Africa")
                .email("emeka_micheal_001@billford.africa")
                .ownerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"))
                .build();

        CreatedMerchant merchant = merchantSvc.create(auth, req);

        System.out.println(Utilities.objectToJSON(merchant));

        assertThat(merchant).isNotNull();

    }

    @Test
    public void retrieve() {

        Merchant merchant = merchantSvc.retrieveById(UUID.fromString("96498689-0327-40fa-9e8a-91ca6936a122"));

        System.out.println(Utilities.objectToJSON(merchant));

        assertThat(merchant).isNotNull();
    }

    @Test
    @SneakyThrows
    public void update() {

        Merchant merchant = merchantSvc.retrieveById(UUID.fromString("96498689-0327-40fa-9e8a-91ca6936a122"));

        merchant.setEmail("abiodun@billford.africa");

        Merchant updatedMerchant = merchantSvc.update(auth, merchant);

        System.out.println(Utilities.objectToJSON(updatedMerchant));

        assertThat(updatedMerchant).isNotNull();

    }


    @Test
    public void delete() {
        Boolean isDeleted = merchantSvc.deleteById(auth, UUID.fromString("96498689-0327-40fa-9e8a-91ca6936a122"));

        System.out.println(String.format("Merchant isDelete %s ", isDeleted));

        assertThat(isDeleted).isTrue();
    }


}
