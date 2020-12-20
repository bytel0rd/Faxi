package com.avenuer.faxi.shared.formatters;

import lombok.Data;
import org.joda.time.Instant;
import org.springframework.http.HttpStatus;

@Data
public class Response<T> {
    private T data;
    private long date = Instant.now().getMillis();
    private HttpStatus status;

    Response(T data) {
        this.data = data;
    }
}
