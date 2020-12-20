package com.avenuer.faxi.authentication.params;

import com.avenuer.faxi.authentication.models.AuthProfile;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthorizedUser {

    private String accessToken;
    private AuthProfile profile;
}
