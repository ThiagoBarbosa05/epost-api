package com.thiago.epost.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiago.epost.auth.dto.TokenResponseDTO;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TestUtils {

    public static String objectToJSON(Object obj) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static UUID generateRandomUUID() {
        return UUID.randomUUID();
    }

    public static TokenResponseDTO generateToken(String subject, String secret) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(10));
        String token = JWT.create()
                .withIssuer("epost")
                .withSubject(subject)
                .withClaim("roles", List.of("USER"))
                .withExpiresAt(expiresAt)
                .sign(algorithm);

        return new TokenResponseDTO(
                token,
                expiresAt.toEpochMilli()
        );
    }
}
