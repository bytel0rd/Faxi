package com.avenuer.faxi.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.UUID;

public class Utilities {

    @SneakyThrows
    public static String objectToJSON(Object body) {

        if (body == null) throw new Exception("Utilites cannot convert null values to JSON");

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(body);
    }

    public static String randomString(int count) {
        if (count > 12 ) count = 12;
        return UUID.randomUUID().toString().substring(0, count).toUpperCase().toString();
    }

}
