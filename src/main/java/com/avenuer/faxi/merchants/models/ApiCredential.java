package com.avenuer.faxi.merchants.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "api_credentials")
public class ApiCredential {

    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private UUID merchantId;

    @Column(nullable = false)
    private String secretKey;
    @Column(nullable = false)
    private String secretPass;

    private String expiringDate;
    private String whiteListedIPs;

    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;

    @CreatedDate
    private Date dateCreated;
    @LastModifiedDate
    private Date dateModified;

    public String getApiKey() {
        return Base64.getEncoder().encodeToString(String.format("%s:%s", this.secretKey, this.secretPass).getBytes());
    }

}
