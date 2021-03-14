package com.avenuer.faxi.merchants.params;

import com.avenuer.faxi.merchants.models.Merchant;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
//@Builder
@EqualsAndHashCode(callSuper = true)
public class CreatedMerchant extends Merchant {
    private String liveAPIKey;
    private String testAPIKey;
}
