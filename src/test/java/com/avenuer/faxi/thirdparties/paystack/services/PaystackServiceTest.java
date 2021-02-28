package com.avenuer.faxi.thirdparties.paystack.services;

import com.avenuer.faxi.shared.Utilities;
import com.avenuer.faxi.thirdparties.paystack.params.CreatePaystackCustomer;
import com.avenuer.faxi.thirdparties.paystack.params.PaystackCreatedCustomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaystackServiceTest {

    @Autowired
    private PaystackService paystack;

    @Test
    public void createPaystackCustomer() throws Exception {
        CreatePaystackCustomer customer = CreatePaystackCustomer.builder()
                .email("test_b_billford@faxi.com")
                .firstName("billford")
                .lastName("faxi")
                .build();

        PaystackCreatedCustomer createdCustomer = paystack.createCustomer(customer);

        System.out.println(Utilities.objectToJSON(createdCustomer));

    }

    @Test
    public void getPaystackCustomer() {
        PaystackCreatedCustomer createdCustomer = paystack.getCustomer("test_b_billford@faxi.com");

        System.out.println(Utilities.objectToJSON(createdCustomer));

        assertThat(createdCustomer).isNotNull();

    }

}
