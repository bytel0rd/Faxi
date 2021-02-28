package com.avenuer.faxi.users.controllers;

import com.avenuer.faxi.authentication.enums.ROLE.ROLE;
import com.avenuer.faxi.authentication.models.AuthProfile;
import com.avenuer.faxi.users.Models.UserProfile;
import com.avenuer.faxi.users.Services.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping(value = "api/v1/users")
@RestController
public class UserController {

    @Autowired
    private UserService userSvc;

    ResponseEntity<UserProfile> updateProfile(@RequestBody UserProfile profile) {

        val currentUser = AuthProfile.builder().id(UUID.randomUUID()).build();

        if (currentUser.getRole() == ROLE.USER) {
            profile.setId(currentUser.getId());
        }

        profile = userSvc.updateUser(currentUser, profile);

        return ResponseEntity.ok(profile);
    }

}
