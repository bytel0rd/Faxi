package com.avenuer.faxi.thirdparties.coinvest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class CoinVestService {

    @Autowired
    private OkHttpClient client;

    public void createCheckOut() {

    }

    private void prepareHeaders(@NotNull Request request) {

    }

}
