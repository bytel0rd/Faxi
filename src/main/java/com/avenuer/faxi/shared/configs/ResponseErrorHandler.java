package com.avenuer.faxi.shared.configs;

import com.avenuer.faxi.shared.formatters.ResponseError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.var;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseErrorHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            e.printStackTrace();

            response.getWriter().write(convertObjectToJson(cleanError(e)));
        }

    }

    private ResponseError cleanError(Exception e) {
        var res = ResponseError.builder()
                .error("Internal Server Error")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();

        if (e instanceof HttpClientErrorException) {
            res.setStatus(((HttpClientErrorException) e).getStatusCode());
            res.setError(((HttpClientErrorException) e).getStatusText());
        }

        return res;
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {

        if (object == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
       String error = mapper.writeValueAsString(object);

       System.out.println(error);

       return error;
    }

}
