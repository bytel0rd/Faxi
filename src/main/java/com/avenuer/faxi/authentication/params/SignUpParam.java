package com.avenuer.faxi.authentication.params;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class SignUpParam {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String password;

    private String appSecret;
}