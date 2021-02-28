package com.avenuer.faxi.shared.formatters;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
@Data
public class ResponseError {

    private String error;
    private String message;
    private HttpStatus status;

}
