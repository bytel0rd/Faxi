package com.avenuer.faxi.thirdparties.paystack.services;

import com.avenuer.faxi.thirdparties.paystack.PaystackClient;
import com.avenuer.faxi.thirdparties.paystack.params.CreatePaystackCustomer;
import com.avenuer.faxi.thirdparties.paystack.params.PaystackCreatedCustomer;
import com.avenuer.faxi.thirdparties.paystack.params.PaystackResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaystackService {

    @Autowired
    private PaystackClient client;

    public PaystackCreatedCustomer createCustomer(CreatePaystackCustomer customer) throws Exception {

        try {
            Response response = client.POST("/customer", customer);

            ObjectMapper jsonMapper = getMapper();

            var res = jsonMapper.readValue(response.body().string(), PaystackResponse.class);

            return  jsonMapper.readValue(jsonMapper.writeValueAsString(res.getData()), PaystackCreatedCustomer.class) ;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("Error trying to create Customer");
        }

    }

    @SneakyThrows
    public PaystackCreatedCustomer getCustomer(String emailAddress) {
        try {
            Response response = client.GET(String.format("/customer/%s", emailAddress));

            ObjectMapper jsonMapper = getMapper();

            var res = jsonMapper.readValue(response.body().string(), PaystackResponse.class);

            return  jsonMapper.readValue(jsonMapper.writeValueAsString(res.getData()), PaystackCreatedCustomer.class) ;

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("Error trying to retrieve Customer information");
        }
    }

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();

       mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        return mapper;
    }

}
