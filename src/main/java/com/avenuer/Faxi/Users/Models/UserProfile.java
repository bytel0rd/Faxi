package com.avenuer.Faxi.Users.Models;

import com.avenuer.Faxi.Users.enums.Gender;
import com.avenuer.Faxi.Users.enums.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user_profiles")
public class UserProfile {
    @Id
    @GeneratedValue
    private UUID id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Product product = Product.USER;

    private String modifiedBy;
    private Date dateCreated;
    private Date dateModified;
}
