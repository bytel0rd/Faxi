package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Services.UserService;
import com.avenuer.faxi.wallets.dao.WalletDoa;
import com.avenuer.faxi.wallets.models.Wallet;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@Service
public class WalletService {

    @Autowired
    private UserService userService;

    @Autowired
    private VirtualNubanService nuban;

    @Autowired
    private WalletDoa walletDoa;

    public Wallet create(@NotNull AuthProfile authProfile, CreateNubanParam ownerInformation) {

        Wallet wallet = null;

        try {
            Wallet walletUpdate = Wallet.builder()
                    .id(UUID.randomUUID())
                    .ownerId(ownerInformation.getOwnerId())
                    .availableBalance(0)
                    .totalBalance(0)
                    .createdBy(authProfile.fullName())
                    .modifiedBy(authProfile.fullName())
                    .build();

            wallet = walletDoa.save(walletUpdate);

            nuban.create(authProfile, ownerInformation);

            return wallet;

        } catch (Exception e) {
            if (wallet != null) {
                return wallet;
            }
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error occurred while creating user wallet, please retry again or contact admin");
        }

    }

    public Wallet update(@NotNull AuthProfile auth, @NonNull Wallet wallet) {
        wallet.setModifiedBy(auth.fullName());
        return walletDoa.save(wallet);
    }

    public Wallet retrieve(UUID walletId) {
        var wallet = walletDoa.findById(walletId);

        if (wallet.isPresent()) {
            return wallet.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Wallet with %s not found", walletId));

    }

    public Wallet retrieveByOwnerId(UUID ownerId) {
        var wallet = walletDoa.findByOwnerId(ownerId);

        if (wallet.isPresent()) {
            return wallet.get();
        }

        throw new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("Wallet for %s owner not found", ownerId));

    }

    public Boolean delete(@NotNull AuthProfile auth, UUID walletId) {
        try {
            walletDoa.deleteById(walletId);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error processing request at the moment, please try again ");
        }
    }

}
