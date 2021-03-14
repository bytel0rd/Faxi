package com.avenuer.faxi.merchants.params;

import com.avenuer.faxi.shared.EnvironmentMode;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;
import java.util.Date;

@Data
@Builder
public class CreateApiCredentialParam {

    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private UUID merchantId;

    @NotNull
    private EnvironmentMode mode;

    private Date expiringDate;
    private String whiteListedIPs;

}
