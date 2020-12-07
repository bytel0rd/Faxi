package com.avenuer.faxi.authentication.models;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.users.enums.Product;
import lombok.Builder;
import lombok.Data;
import org.apache.catalina.Role;

import java.util.UUID;

@Data
@Builder
public class AuthProfile {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Product product;
    private ROLE role;

    public String fullName() {
        return firstName + " " + lastName;
    }

}
