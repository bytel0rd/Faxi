package com.avenuer.Faxi.Authentication.models;

import lombok.Builder;

import javax.persistence.Entity;

@Builder
@Entity
public class AuthCredential {
    private String email;
    private String passwordHash;
    private String Id;
}