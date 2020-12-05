package com.avenuer.Faxi.Users.Params;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CreateUserParam {
    private String firstName;
    private String lastName;
    private UUID userId;
}
