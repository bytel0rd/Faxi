package com.avenuer.faxi.merchants.params;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@Builder
public class CreateMerchantParam {

    @NotNull
    private UUID ownerId;
    @NotNull
    private String name;
    @NotNull
    private String email;

}
