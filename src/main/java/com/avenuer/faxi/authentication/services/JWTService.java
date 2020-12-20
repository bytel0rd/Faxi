package com.avenuer.faxi.authentication.services;

import com.avenuer.faxi.authentication.models.AuthProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Service
public class JWTService {

    @Value("${faxi.jwt.secret}")
    private String jwtSecretKey;

    @Value("${faxi.jwt.daysToExpire}")
    private String daysToExpire;

    public String createToken(AuthProfile profile) {

        if (profile == null) {

            throw new RuntimeException("Invalid AuthProfile provided for token creation");

        }

        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> map = mapper.convertValue(profile, Map.class);

        Claims claim = Jwts.claims()
                .setSubject(profile.fullName())
                .setAudience(profile.fullName())
                .setIssuedAt(Instant.now().toDate())
                .setIssuer("faxi.ng")
                .setId(profile.getId().toString());

        claim.putAll(map);

        System.out.println(jwtSecretKey + " : " + daysToExpire);

        String token = Jwts.builder().setClaims(claim).signWith(SignatureAlgorithm.HS256, jwtSecretKey).compact();

        return token;
    }

    public AuthProfile decryptToken(String token) {

        try {

            Claims body = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return mapper.readValue(mapper.writeValueAsString(body), AuthProfile.class);

        } catch (Exception  e) {

            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

        }
    }

}
