package com.avenuer.faxi.users.Models;

import com.avenuer.faxi.users.enums.Gender;
import com.avenuer.faxi.users.enums.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_profiles")
public class UserProfile {

    @JsonIgnore
    @Id
    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Product product = Product.USER;

    private String modifiedBy;
    private Date dateCreated;
    private Date dateModified;
}
