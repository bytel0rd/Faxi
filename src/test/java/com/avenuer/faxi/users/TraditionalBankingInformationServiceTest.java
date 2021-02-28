package com.avenuer.faxi.users;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Params.BVNUpdateParam;
import com.avenuer.faxi.users.Services.TraditionalBankingInformationService;
import lombok.var;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraditionalBankingInformationServiceTest {

    @Autowired
    private TraditionalBankingInformationService bankingInformationService;

    AuthProfile currentUser = AuthProfile.builder()
            .firstName("Micheal")
            .lastName("Emeka")
            .build();

    @Test
    public void updateBvnUpdateSuccessful() {
        BVNUpdateParam bvnInfo = BVNUpdateParam.builder()
                .BVN("12345678901")
                .dob("12-05-2000")
                .userId("random-userId")
                .build();

        var information = bankingInformationService.updateBvnInformation(currentUser, bvnInfo);
        assertThat(information.getBVN()).isEqualTo(bvnInfo.getBVN());
    }

    @Test
    public void BVNDobMatchError() {
        BVNUpdateParam bvnInfo = BVNUpdateParam.builder()
                .BVN("12345678901")
                .dob("12-05-2000")
                .userId("random-userId")
                .build();

        var information = bankingInformationService.updateBvnInformation(currentUser, bvnInfo);
        assertThatThrownBy(() -> information.getBVN()).hasMessageContaining("BVN verification");
    }

}
