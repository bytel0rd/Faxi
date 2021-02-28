package com.avenuer.faxi.thirdparties.paystack.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaystackCreatedCustomer {
    private String email;
    private Long integration;
    private String domain;
    private String customerCode;
    private Long id;
    private Boolean identified;
    private Object identifications;
    private Date createdAt;
    private Date updatedAt;
}
