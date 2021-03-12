package com.avenuer.faxi.users.Params;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Builder
@Data
public class CreateUserParam {
    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private UUID userId;
}
