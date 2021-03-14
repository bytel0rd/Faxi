package com.avenuer.faxi.merchants;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.merchants.models.ApiCredential;
import com.avenuer.faxi.merchants.params.CreateApiCredentialParam;
import com.avenuer.faxi.merchants.services.ApiCredentialService;
import com.avenuer.faxi.shared.Utilities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiCredentialServiceTest {

    @Autowired
    private ApiCredentialService credentialSvc;

    private AuthProfile auth = AuthProfile.builder()
            .firstName("Emeka")
            .lastName("Test")
            .role(ROLE.ADMIN)
            .build();


    @Test
    public void create() {

        CreateApiCredentialParam req = CreateApiCredentialParam.builder()
                .name("BillFord Africa")
                .merchantId(UUID.randomUUID())
                .email("emeka_micheal_001@billford.africa")
                .build();

        ApiCredential credential = credentialSvc.create(auth, req);

        System.out.println(Utilities.objectToJSON(credential));

        assertThat(credential).isNotNull();

    }

    @Test
    public void retrieve() {

        ApiCredential credential = credentialSvc.retrieve(UUID.fromString("91f1b32b-1600-4f97-8931-acc4684c7420"));

        System.out.println(Utilities.objectToJSON(credential));

        assertThat(credential).isNotNull();
    }

    @Test
    public void retrieveWithMerchantId() {
        ApiCredential credential = credentialSvc.retrieveByMerchantId(UUID.fromString("781fc4ad-c540-4473-a732-9ff762aa6977"));

        System.out.println(Utilities.objectToJSON(credential));

        assertThat(credential).isNotNull();
    }


    @Test
    public void delete() {
        Boolean isDeleted = credentialSvc.delete(auth, UUID.fromString("91f1b32b-1600-4f97-8931-acc4684c7420"));

        System.out.println(String.format("Credential isDelete %s ", isDeleted));

        assertThat(isDeleted).isTrue();
    }


}
