package com.avenuer.faxi.wallets.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "wallets")
public class Wallet {

    @JsonIgnore
    @Id
    @GeneratedValue
    private UUID id;

    private double availableBalance = 0;

    private double totalBalance = 0;

    @Column(nullable = false)
    private String modifiedBy;

    @Column(unique = true, nullable = false)
    private String userId;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateModified;

    @Column(nullable = false)
    private String createdBy;
}
