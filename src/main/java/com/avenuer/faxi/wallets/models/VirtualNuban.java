package com.avenuer.faxi.wallets.models;

import com.avenuer.faxi.wallets.enums.Currency;
import com.avenuer.faxi.wallets.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "virtual_nubans")
@EntityListeners(AuditingEntityListener.class)
public class VirtualNuban {

    @Id
    @GeneratedValue
    private UUID id;
    private UUID ownerId;

    private String accountNumber;
    private String bankName;

    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Boolean isActive = true;

    private String accountReference;
    private String issuerReference;

    @Enumerated(EnumType.STRING)
    private PaymentGateway issuer;

    private String createdBy;
    private String modifiedBy;

    @LastModifiedDate
    private Date dateModified;
    @CreatedDate
    private String dateCreated;

}
