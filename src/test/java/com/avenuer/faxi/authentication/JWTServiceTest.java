package com.avenuer.faxi.authentication;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.services.JWTService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class JWTServiceTest {

    @Autowired
    private JWTService jwt;

    private String encodedToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhYmlvZHVuIG95ZWdva2UiLCJhdWQiOiJhYmlvZHVuIG95ZWdva2UiLCJpYXQiOjE2MDczNjQ5MjIsImlzcyI6ImZheGkubmciLCJqdGkiOiJkOTEwMzU1Ny0zNWZhLTQwMjctOGZmYi0yOTg0MTE5MGVkZjYiLCJpZCI6ImQ5MTAzNTU3LTM1ZmEtNDAyNy04ZmZiLTI5ODQxMTkwZWRmNiIsImZpcnN0TmFtZSI6ImFiaW9kdW4iLCJsYXN0TmFtZSI6Im95ZWdva2UiLCJlbWFpbCI6ImFiaW9kdW5AZmF4aS5jb20iLCJyb2xlIjoiQURNSU4ifQ.wKf27HZfRM8BlT17UawkddCySWx-L6nI_eOSEkZCHVw";

    @Test
    void createTokenForEmptyProfile() {

        assertThatThrownBy(() -> jwt.createToken(null))
                .hasMessageContaining("Invalid AuthProfile provided");
    }

    @Test
    void createProfileToken() {

        val profile = AuthProfile.builder()
                .email("abiodun@faxi.com")
                .id(UUID.randomUUID())
                .role(ROLE.ADMIN)
                .firstName("abiodun")
                .lastName("oyegoke")
                .build();

        String token = jwt.createToken(profile);

        assertThat(token).isInstanceOfAny(String.class);

        System.out.println(token);
    }

    @Test
    void decryptBadToken() {

        assertThatThrownBy(() -> jwt.decryptToken("encodedToken")).isInstanceOf(Exception.class);

    }

    @Test
    void decryptToken() {

        assertThat(jwt.decryptToken(encodedToken)).isInstanceOf(AuthProfile.class);

    }
}
