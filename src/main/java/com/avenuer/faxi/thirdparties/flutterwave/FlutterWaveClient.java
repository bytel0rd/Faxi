package com.avenuer.faxi.thirdparties.flutterwave;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.var;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlutterWaveClient {

    @Value("${flutterwave.auth.secretKey}")
    private String secretKey;

    @Autowired
    private OkHttpClient client;

    @SneakyThrows
    public Response GET(String url) {

        ObjectMapper objectMapper = new ObjectMapper();

        Request req = new Request.Builder()
                .url(appendUrl(url))
                .addHeader("AUTHORIZATION", String.format("Bearer %s", secretKey))
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(req);
        return call.execute();
    }

    @SneakyThrows
    public Response POST(String url, Object body) {

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                objectMapper.writeValueAsString(cleanBodyParam(body)));

        Request req = new Request.Builder()
                .url(appendUrl(url))
                .post(requestBody)
                .addHeader("AUTHORIZATION", String.format("Bearer %s", secretKey))
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(req);
        return call.execute();
    }

    @SneakyThrows
    public Response PUT(String url, Object body) {

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                objectMapper.writeValueAsString(cleanBodyParam(body)));

        Request req = new Request.Builder()
                .url(appendUrl(url))
                .put(requestBody)
                .addHeader("AUTHORIZATION", String.format("Bearer %s", secretKey))
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(req);
        return call.execute();
    }

    @SneakyThrows
    public Response DELETE(String url, Object body) {

        ObjectMapper objectMapper = new ObjectMapper();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                objectMapper.writeValueAsString(cleanBodyParam(body)));

        Request req = new Request.Builder()
                .url(appendUrl(url))
                .delete(requestBody)
                .addHeader("AUTHORIZATION", String.format("Bearer %s", secretKey))
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(req);
        return  call.execute();
    }

    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return  objectMapper;
    }

    private Object cleanBodyParam(Object body) {
        return body == null ? new Object() : body;
    }

    private String appendUrl(String url) {

        if (url != null &&url.startsWith("/")) {
            var builder = new StringBuilder();
            url = builder.append(url).deleteCharAt(0).toString();
        }

        return String.format("https://api.flutterwave.com/v3/%s", url);
    }
}
