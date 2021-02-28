package com.avenuer.faxi.authentication.services;

import com.avenuer.faxi.authentication.doa.AuthCredentialDoa;
import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthCredential;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.params.SignUpParam;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Params.CreateUserParam;
import com.avenuer.faxi.users.Services.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
public class CreateCredentialService {

    @Autowired
    private UserService users;

    @Autowired
    private PasswordService passwordSvc;

    @Autowired
    private AuthCredentialDoa authDoa;

    @Value("faxi.app.secret")
    private String appSecret;

    @SneakyThrows
    public AuthProfile signUp(SignUpParam credential) {
        UUID id = UUID.randomUUID();

        AuthCredential authCredential = AuthCredential.builder()
                .email(credential.getEmail())
                .role(confirmRequestedRole(credential.getAppSecret()))
                .Id(id)
                .passwordHash(passwordSvc.createHash(credential.getPassword()))
                .build();

        try {

            authCredential = authDoa.save(authCredential);
            log.debug("Saved AuthCredential for %s ", authCredential.getEmail());

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Authentication credentials with e-mail already exist");
        }

        return createUserProfile(credential, authCredential);
    }


    private AuthProfile createUserProfile(SignUpParam credential, AuthCredential authCredential) {
        try {

            CreateUserParam user = CreateUserParam.builder()
                    .firstName(credential.getFirstName())
                    .lastName(credential.getLastName())
                    .userId(authCredential.getId())
                    .build();

            AuthProfile authProfile = AuthProfile.builder()
                    .id(user.getUserId())
                    .role(authCredential.getRole())
                    .email(authCredential.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();

            UserProfile profile = users.createUser(authProfile, user);
            authProfile.setProduct(profile.getProduct());

            return authProfile;

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            authDoa.deleteById(authCredential.getId().toString());

            throw e;

        }
    }

    @SneakyThrows
    private ROLE confirmRequestedRole(String userAppSecret) {
        if (userAppSecret == null) return ROLE.USER;
        if (appSecret.equals(userAppSecret)) return ROLE.ADMIN;
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ROLE Requested, please confirm request Parameters");
    }

}
