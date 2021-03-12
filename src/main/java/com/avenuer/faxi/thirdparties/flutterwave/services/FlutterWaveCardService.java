package com.avenuer.faxi.thirdparties.flutterwave.services;

import com.avenuer.faxi.thirdparties.flutterwave.FlutterWaveClient;
import com.avenuer.faxi.thirdparties.flutterwave.enums.FlutterWardCardStatus;
import com.avenuer.faxi.thirdparties.flutterwave.params.*;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FlutterWaveCardService {

    @Autowired
    private FlutterWaveClient client;

    public FlutterWaveCreatedCard create(@NonNull FlutterWaveCardRequest card) {

        try {

            Response response = client.POST("/virtual-cards", card);

            FlutterWaveResponse<FlutterWaveCreatedCard> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<FlutterWaveCreatedCard>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            if (flutterResponse.getData() != null) {
                return flutterResponse.getData();
            }

            throw  new RuntimeException(flutterResponse.getMessage());

        } catch (Exception e) {

            log.error(String.format("Error creating %s virtual card", card.getBillingName()), e);
            throw new RuntimeException("Error Occur while trying to create virtual card for user");

        }

    }


    public FlutterWaveCreatedCard retrieve(@NonNull String cardId) {

        try {

            Response response = client.GET(String.format("/virtual-cards/%s", cardId));

            FlutterWaveResponse<FlutterWaveCreatedCard> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<FlutterWaveCreatedCard>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return flutterResponse.getData();

        } catch (Exception e) {

            log.error(String.format("Error retrieving %s virtual card", cardId), e);
            throw new RuntimeException("Error Occur while trying to retrieve virtual card for details");

        }

    }

    public boolean fund(@NonNull String cardId, @NonNull FlutterWaveCardFund fund) {

        try {

            Response response = client.POST(String.format("/virtual-cards/%s/fund", cardId), fund);

            FlutterWaveResponse<Object> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<Object>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return true;

        } catch (Exception e) {

            log.error(String.format("Error funding %s virtual card", cardId), e);
            throw new RuntimeException("Error Occur while trying to fund virtual card");

        }

    }

    public boolean withdrawFrom(@NonNull String cardId, @NonNull FlutterWaveCardWithdraw withdrawal) {

        try {

            Response response = client.POST(String.format("/virtual-cards/%s/withdraw", cardId), withdrawal);

            FlutterWaveResponse<Object> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<Object>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return true;

        } catch (Exception e) {

            log.error(String.format("Error withdrawing from %s virtual card", cardId), e);
            throw new RuntimeException("Error Occur while trying to withdraw from virtual card");

        }

    }

    public boolean setCardStatus(@NonNull String cardId, @NonNull FlutterWardCardStatus status) {

        try {

            Response response = client.PUT(String.format("/virtual-cards/%s/status/%s", cardId, status), null);

            FlutterWaveResponse<Object> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<Object>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return true;

        } catch (Exception e) {

            log.error(String.format("Error setting %s virtual card status to %s", cardId, status), e);
            throw new RuntimeException(String.format("Error Occur while trying to %s from virtual card", status));

        }

    }

    public boolean terminateCard(@NonNull String cardId) {
        try {

            Response response = client.PUT(String.format("/virtual-cards/%s/terminate", cardId), null);

            FlutterWaveResponse<Object> flutterResponse = client.mapper()
                    .readValue(response.body().string(),
                            new TypeReference<FlutterWaveResponse<Object>>() {});

            log.info(String.format("FlutterWave: %s", flutterResponse.getMessage()));

            return true;

        } catch (Exception e) {

            log.error(String.format("Error setting %s virtual card status", cardId), e);
            throw new RuntimeException(String.format("Error Occur while trying terminate from virtual card"));

        }
    }
}
