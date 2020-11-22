package com.avenuer.Faxi.Authentication.params;

import lombok.Builder;

@Builder
public class SignUpParam {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}