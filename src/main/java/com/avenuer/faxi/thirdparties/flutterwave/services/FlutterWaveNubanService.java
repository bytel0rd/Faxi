package com.avenuer.faxi.thirdparties.flutterwave.services;

import com.avenuer.faxi.thirdparties.flutterwave.FlutterWaveClient;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterNubanAccount;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWareNubanReq;
import com.avenuer.faxi.thirdparties.flutterwave.params.FlutterWaveResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FlutterWaveNubanService {

    @Autowired
    private FlutterWaveClient client;

    public FlutterNubanAccount create(FlutterWareNubanReq nubanReq) {

        try {

            Response response = client.POST("/virtual-account-numbers", nubanReq);

            FlutterWaveResponse<FlutterNubanAccount> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<FlutterNubanAccount>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return flutterResponse.getData();

        } catch (Exception e) {

            log.error(String.format("Error creating %s virtual account details", nubanReq.getAccountHolder()), e);
            throw new RuntimeException("Error Occur while trying to create virtual account details");

        }

    }

    public FlutterNubanAccount retrieve(String orderReference) {

        try {

            Response response = client.GET(String.format("/virtual-account-numbers/%s", orderReference));

            FlutterWaveResponse<FlutterNubanAccount> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<FlutterNubanAccount>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return flutterResponse.getData();

        } catch (Exception e) {

            log.error(String.format("Error retrieving %s virtual account details", orderReference), e);
            throw new RuntimeException("Error Occur while trying to retrieve virtual account details");

        }

    }
}
