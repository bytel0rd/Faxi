package com.avenuer.faxi.wallets.models;

import com.avenuer.faxi.wallets.enums.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "virtual_cards")
public class VirtualCard {

    @Id
    private UUID id;
    @Column(nullable = false)
    private UUID ownerId;

    @Enumerated(EnumType.STRING)
    private PaymentGateway provider;
    private Long accountID;
    private Boolean isActive;

    private String cardType;
    private String nameOnCard;
    private String amount;
    private String currency;
    private String cvv;
    private String expiration;

    private UUID cardHash;
    private String cardPan;
    private String maskedPan;

    private String address1;
    private String zipCode;
    private String city;
    private String state;

    private String createdBy;
    private String modifiedBy;

    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateModified;

}
