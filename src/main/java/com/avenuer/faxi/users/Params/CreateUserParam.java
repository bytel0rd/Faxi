package com.avenuer.faxi.users.Params;

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
