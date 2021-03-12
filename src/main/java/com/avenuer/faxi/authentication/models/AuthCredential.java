package com.avenuer.faxi.authentication.models;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "auth_credentials")
public class AuthCredential {
    @Id
    @GeneratedValue
    private UUID Id;

    @Column(unique = true)
    private String email;
    private String passwordHash;

    private ROLE role;

    private String modifiedBy;
    private Date dateCreated;
    private Date dateModified;
}