package com.avenuer.faxi.authentication.services;

/**
 * Oyegoke Abiodun
 * 27/11/2020
 */

import lombok.SneakyThrows;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @SneakyThrows
    public String createHash(String password)  {
        if (password == null || password.isEmpty()) {
            throw new Exception("A valid Password is required");
        }
        return encoder.encode(password);
    }

    @SneakyThrows
    public boolean compareHashAndPassword(String passwordHash, String password) {
        if (passwordHash == null || password == null) {
            throw new Exception("Unmet Password Matching Pre-Condition");
        }
        return encoder.matches(passwordHash, password);
    }

    public PasswordEncoder getPasswordEncoder() {
        return encoder;
    }

}
