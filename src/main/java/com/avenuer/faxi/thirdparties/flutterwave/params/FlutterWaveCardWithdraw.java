package com.avenuer.faxi.thirdparties.flutterwave.params;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlutterWaveCardWithdraw {
    private int amount;
}
