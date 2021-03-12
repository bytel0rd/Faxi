package com.avenuer.faxi.users.Models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity()
public class TraditionalBankingInformation {

    @Id
    private UUID id;

    private String BVN;

    @Column(unique = true,nullable = false)
    private String userId;

    private String bankCode;

    private String bankAccountNumber;

    private String bankAccountName;

    @CreatedDate
    private Date dateCreated;

    @LastModifiedDate
    private Date dateModified;

    @Column(nullable = false)
    private String modifiedBy;

}
