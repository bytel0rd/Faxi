package com.avenuer.faxi.wallets.models;

import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "virtual_nubans")
public class VirtualNuban {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;

    private String accountNumber;
    private String bankName;
    private Currency currency;
    private Boolean isActive = true;

    private String accountReference;
    private String issuerReference;

    private PaymentGateway issuer;

    private String createdBy;
    private String modifiedBy;

    @LastModifiedDate
    private Date dateModified;
    @CreatedDate
    private String dateCreated;

}
