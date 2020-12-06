package com.avenuer.faxi.authentication.services;

import com.avenuer.faxi.authentication.models.AuthCredential;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.UserProfile;

public class AuthUtils {

    public static AuthProfile createAuthProfile(AuthCredential authCredential, UserProfile profile) {
        return AuthProfile.builder()
                .email(authCredential.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .id(profile.getId())
                .product(profile.getProduct())
                .build();
    }
}
