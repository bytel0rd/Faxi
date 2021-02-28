package com.avenuer.faxi.thirdparties.flutterwave.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlutterWaveResponse<T> {
    private String status;
    private String message;
    private T data;
}
