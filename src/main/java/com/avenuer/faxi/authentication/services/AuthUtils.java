package com.avenuer.faxi.authentication.services;

import com.avenuer.faxi.authentication.models.AuthCredential;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Params.CreateUserParam;

public class AuthUtils {

    public static AuthProfile createAuthProfile(AuthCredential authCredential, UserProfile profile) {
        return AuthProfile.builder()
                .email(authCredential.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .id(profile.getId())
                .product(profile.getProduct())
                .role(authCredential.getRole())
                .build();
    }

}
