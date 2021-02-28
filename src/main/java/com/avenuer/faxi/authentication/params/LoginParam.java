package com.avenuer.faxi.authentication.params;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class LoginParam {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
