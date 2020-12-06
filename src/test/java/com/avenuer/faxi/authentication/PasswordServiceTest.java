package com.avenuer.faxi.authentication;

import com.avenuer.faxi.authentication.services.PasswordService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PasswordServiceTest {

    private PasswordService passwordSvc = new PasswordService();
    private String testPassword = "12345AbcdEf";

    @Test
    void successfulHashCreation() {
        assertThat(passwordSvc.createHash(testPassword))
                .as("Should create password hash for %s", testPassword)
                .isInstanceOf(String.class);
    }

    @Test
    void failingPasswordHashCreation() {

        String[] invalidPasswords = new String[]{ "", null };
        for (String password:
                invalidPasswords) {
            assertThatThrownBy(() -> passwordSvc.createHash(password))
                    .as("Should throw Error for %s while hashing", password);
        }
    }

    @Test
    void comparePassword() {
        assertThatThrownBy(() -> passwordSvc.compareHashAndPassword(null, testPassword))
                .as("should throw error for not matching precondition of required hash");
        assertThatThrownBy(() -> passwordSvc.compareHashAndPassword(testPassword, null))
                .as("should throw error for not matching precondition of required password");
    }
}
