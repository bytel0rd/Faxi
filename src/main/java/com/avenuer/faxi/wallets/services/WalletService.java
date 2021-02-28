package com.avenuer.faxi.wallets.services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Services.UserService;
import com.avenuer.faxi.wallets.params.CreateNubanParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WalletService {

    @Autowired
    private UserService userService;

    @Autowired
    private VirtualNubanService nuban;

    public void createWallet(AuthProfile authProfile, CreateNubanParam userInformation) {

        nuban.create(authProfile, userInformation);

    }

    public void updateWallet() {

    }

    public void retrieve() {

    }

    public void deleteWallet() {

    }

}
