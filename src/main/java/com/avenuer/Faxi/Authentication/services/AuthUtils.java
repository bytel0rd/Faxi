package com.avenuer.Faxi.Authentication.services;

import com.avenuer.Faxi.Authentication.models.AuthCredential;
import com.avenuer.Faxi.Authentication.models.AuthProfile;
import com.avenuer.Faxi.Users.Models.UserProfile;

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
