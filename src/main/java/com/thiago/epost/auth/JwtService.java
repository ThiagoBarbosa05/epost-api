package com.thiago.epost.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.thiago.epost.auth.dto.TokenResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    @Value("${security.token.secret}")
    private String secretKey;

    public TokenResponseDTO generateToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
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

    public DecodedJWT validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        try {
            return JWT
                    .require(algorithm)
                    .withIssuer("epost")
                    .build()
                    .verify(token);
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}
