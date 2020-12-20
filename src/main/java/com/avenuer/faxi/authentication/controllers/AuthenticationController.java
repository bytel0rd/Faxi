package com.avenuer.faxi.authentication.controllers;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.authentication.params.AuthorizedUser;
import com.avenuer.faxi.authentication.params.LoginParam;
import com.avenuer.faxi.authentication.params.SignUpParam;
import com.avenuer.faxi.authentication.services.AuthenticationService;
import com.avenuer.faxi.authentication.services.CreateCredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "api/v1/auth")
@RestController
public class AuthenticationController {

    @Autowired
    CreateCredentialService createCredentialSvc;

    @Autowired
    AuthenticationService authSvc;

    @PostMapping(path = "/signup")
    ResponseEntity<AuthProfile> signUp(@RequestBody SignUpParam credential) {
        return ResponseEntity.ok().body(createCredentialSvc.signUp(credential));
    }

    @PostMapping(path = "/login")
    ResponseEntity<AuthorizedUser> login(@RequestBody LoginParam credential) {
        return ResponseEntity.ok().body(authSvc.login(credential));
    }

    @GetMapping(path = "/token")
    ResponseEntity<AuthProfile> login(@RequestParam(defaultValue = "") String token) {
        return ResponseEntity.ok().body(authSvc.validateToken(token));
    }

}
