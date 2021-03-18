package com.avenuer.faxi.thirdparties.coinvest.params;

import lombok.Builder;
import lombok.Data;
import org.joda.time.Instant;

@Data
@Builder
public class CoinVestDigest {

    private String apiRoute;
    private String apiKey;
    private Object body;
    private final Long timeStamp = Instant.now().getMillis();

    public String getDigest() {

        if (apiRoute == null || apiKey == null) {
            throw new  RuntimeException("API Route & API Key is required for request");
        }

        return null;

    }
}
