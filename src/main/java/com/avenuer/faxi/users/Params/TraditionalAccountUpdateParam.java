package com.avenuer.faxi.users.Params;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class TraditionalAccountUpdateParam {
    @NonNull
    private String bankCode;

    @NonNull
    private String bankAccountNumber;

    private String ownerId;
}
