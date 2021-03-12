package com.avenuer.faxi.wallets.models;

import com.avenuer.faxi.wallets.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Entity(name = "wallet_transactions")
public class WalletTransaction {

    @JsonIgnore
    @Id
    @GeneratedValue
    private UUID id;

    private double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private String reference;

    @Column(nullable = false)
    private String ownerId;

    @Column(nullable = false)
    private String modifiedBy;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateModified;

}
