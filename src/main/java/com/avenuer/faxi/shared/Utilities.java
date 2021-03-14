package com.avenuer.faxi.shared;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.HashSet;
import java.util.Set;
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

    public static String[] getNullPropertyNames (Object source) {

        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
