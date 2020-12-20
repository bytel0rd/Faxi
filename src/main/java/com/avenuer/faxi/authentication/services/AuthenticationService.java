package com.avenuer.faxi.authentication.services;

import com.avenuer.faxi.authentication.doa.AuthCredentialDoa;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.params.AuthorizedUser;
import com.avenuer.faxi.authentication.params.LoginParam;
import com.avenuer.faxi.users.Services.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthenticationService {

    @Autowired
    private UserService users;

    @Autowired
    private PasswordService passwordSvc;

    @Autowired
    private JWTService jwt;

    @Autowired
    private AuthCredentialDoa authDoa;

    @SneakyThrows
    public AuthorizedUser login(LoginParam credential) {

        val savedCredential = authDoa.findOneByEmail(credential.getEmail());

        if (!savedCredential.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login Credentials not available, please Register valid credentials before Login.");
        }

        if (passwordSvc.compareHashAndPassword(savedCredential.get().getPasswordHash(), credential.getPassword())) {
            val user = users.getUserById(savedCredential.get().getId().toString());

            if (user.isPresent()) {

                val profile = AuthUtils.createAuthProfile(savedCredential.get(), user.get());

                AuthorizedUser authorization = AuthorizedUser.builder()
                        .accessToken(jwt.createToken(profile))
                        .profile(profile)
                        .build();

                return authorization;

            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User Profile Not Found, please contact Customer care for more info.");

        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Password provided for authentication");

    }

    @SneakyThrows
    public AuthProfile validateToken(String token) {

        if (token == null ||  StringUtils.isEmpty(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validating Token is missing in the request payload");
        }

        try {
            val profile = jwt.decryptToken(token);

            return profile;

        } catch (Exception e) {
            log.error("Token Validation Error", e);

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Authorization Token Provided.");

        }

    }

}
