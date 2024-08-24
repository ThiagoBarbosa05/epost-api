package com.thiago.epost.auth;

import com.thiago.epost.auth.dto.TokenResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService  jwtService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "secretKey", "fake-secret-key");
    }

    @Test
    void should_to_generate_a_token() {
        UUID subject = UUID.randomUUID();
        TokenResponseDTO tokenResponse = jwtService.generateToken(subject.toString());

        assertNotNull(tokenResponse);
        assertNotNull(tokenResponse.accessToken());
    }
}