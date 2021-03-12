package com.avenuer.faxi.wallets;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import com.avenuer.faxi.wallets.models.Wallet;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import com.avenuer.faxi.wallets.services.WalletService;
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
public class WalletServiceTest {

    @Autowired
    private WalletService walletSvc;

    private AuthProfile auth = AuthProfile.builder()
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
                .ownerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a3"))
                .build();

        Wallet wallet = walletSvc.create(auth, req);

        System.out.println(Utilities.objectToJSON(wallet));

        assertThat(wallet).isNotNull();

    }

    @Test
    public void retrieve() {

        Wallet wallet = walletSvc.retrieve(UUID.fromString("9974c792-e51e-49c8-805d-857cd99c68fc"));

        System.out.println(Utilities.objectToJSON(wallet));

        assertThat(wallet).isNotNull();
    }

    @Test
    public void retrieveWithOwnerId() {
        Wallet wallet = walletSvc.retrieveByOwnerId(UUID.fromString("a4d02a63-f93b-4e6f-b645-ef47fc6c9a03"));

        System.out.println(Utilities.objectToJSON(wallet));

        assertThat(wallet).isNotNull();
    }

    @Test
    @SneakyThrows
    public void updateWallet() {

        Wallet wallet = walletSvc.retrieve(UUID.fromString("9974c792-e51e-49c8-805d-857cd99c68fc"));

        wallet.setAvailableBalance(10);
        wallet.setTotalBalance(10);

        Wallet updatedWallet = walletSvc.update(auth, wallet);

        System.out.println(Utilities.objectToJSON(updatedWallet));

        assertThat(updatedWallet).isNotNull();

    }

    @Test
    public void deleteWallet() {
        Boolean isDeleted = walletSvc.delete(auth, UUID.fromString("9974c792-e51e-49c8-805d-857cd99c68fc"));

        System.out.println(String.format("Wallet isDelete %s ", isDeleted));

        assertThat(isDeleted).isTrue();
    }


}
