package com.avenuer.faxi.thirdparties.paystack.params;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaystackResponse<T> {
    private Boolean status;
    private String message;
    private T data;
}
