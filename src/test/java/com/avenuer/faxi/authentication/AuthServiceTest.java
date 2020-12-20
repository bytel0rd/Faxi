package com.avenuer.faxi.authentication;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest
public class AuthServiceTest {


    @Autowired
    AuthenticationService authSvc;

    @Test
    void login() {

    }

    @Test
    void invalidTokenValidation() {
        String[] tokens = {"", null, "random-token"};
        for (String token : tokens) {
            assertThatThrownBy(() -> authSvc.validateToken(token))
                    .isInstanceOf(ResponseStatusException.class);
        }
    }

    @Test
    void validTokenValidation() {
        String validToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJFbWVrYSBFbWVrYSIsImF1ZCI6IkVtZWthIEVtZWthIiwiaWF0IjoxNjA4NDg5MDE2LCJpc3MiOiJmYXhpLm5nIiwianRpIjoiYTRkMDJhNjMtZjkzYi00ZTZmLWI2NDUtZWY0N2ZjNmM5YTAzIiwiaWQiOiJhNGQwMmE2My1mOTNiLTRlNmYtYjY0NS1lZjQ3ZmM2YzlhMDMiLCJmaXJzdE5hbWUiOiJFbWVrYSIsImxhc3ROYW1lIjoiRW1la2EiLCJlbWFpbCI6ImVtZWthX21pY2hlYWxAZmF4aS5jb20iLCJwcm9kdWN0IjoiVVNFUiIsInJvbGUiOiJVU0VSIn0.eHwO-YutIC3V1NCcYt1XDGSrB6F4JjYxlk2TXKph0pg";
        assertThat(authSvc.validateToken(validToken)).isInstanceOf(AuthProfile.class);
        assertThat(authSvc.validateToken(validToken).getEmail()).isEqualTo("emeka_micheal@faxi.com");
    }

}
