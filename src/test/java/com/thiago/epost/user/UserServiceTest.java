package com.thiago.epost.user;

import com.thiago.epost.auth.JwtService;
import com.thiago.epost.auth.dto.TokenResponseDTO;
import com.thiago.epost.exceptions.InvalidCredentialsException;
import com.thiago.epost.exceptions.ResourceNotFoundException;
import com.thiago.epost.exceptions.UserAlreadyExistsException;
import com.thiago.epost.user.dto.AuthenticateRequestDTO;
import com.thiago.epost.user.dto.CreateUserDTO;
import com.thiago.epost.user.dto.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;



    @Test
    void should_create_user() {
        CreateUserDTO request = new CreateUserDTO("TEST user", "test@email.com", "test-password");
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(request.password())).thenReturn("password-hashed");


        User result = userService.createUser(request);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void should_not_create_user_with_an_existing_email() {
        CreateUserDTO request = new CreateUserDTO("TEST user", "test@email.com", "test-password");
        User userWithEmailExisting = new User();

        when(userRepository.findByEmail(request.email())).thenReturn(Optional.of(userWithEmailExisting));

        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.createUser(request);
        });

        verify(userRepository, times(1)).findByEmail(request.email());
        verify(passwordEncoder, never()).encode(any(String.class));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void should_authenticate_user() {
        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO("user@test.com", "TESTPassword");
        User user = User.builder().id(UUID.randomUUID()).name("TEST user").email("user@test.com").password("TESTPassword").build();
        TokenResponseDTO token = new TokenResponseDTO("access_token", Instant.now().toEpochMilli());

        when(userRepository.findByEmail(authenticateRequestDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authenticateRequestDTO.password(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken(user.getId().toString())).thenReturn(token);

        TokenResponseDTO result = this.userService.authenticate(authenticateRequestDTO);

        verify(userRepository, times(1)).findByEmail(authenticateRequestDTO.email());
        verify(passwordEncoder, times(1)).matches(any(String.class), any(String.class));
        verify(jwtService, times(1)).generateToken(any(String.class));
        assertNotNull(result);
    }

    @Test
    void should_not_be_able_to_authenticate_user_with_invalid_email() {
        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO("invalid@test.com", "TESTPasswordInvalid");

        when(userRepository.findByEmail(authenticateRequestDTO.email())).thenReturn(Optional.empty());

        assertThrows(InvalidCredentialsException.class, () -> {
            this.userService.authenticate(authenticateRequestDTO);
        });

        verify(userRepository, times(1)).findByEmail(authenticateRequestDTO.email());
        verify(passwordEncoder, never()).matches(any(String.class), any(String.class));
        verify(jwtService, never()).generateToken(any(String.class));

    }

    @Test
    void should_not_be_able_to_authenticate_user_with_invalid_password() {
        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO("invalid@test.com", "TESTPasswordInvalid");
        User user = User.builder().id(UUID.randomUUID()).name("TEST user").email("user@test.com").password("TESTPassword").build();

        when(userRepository.findByEmail(authenticateRequestDTO.email())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(authenticateRequestDTO.password(), user.getPassword())).thenReturn(false);

        assertThrows(InvalidCredentialsException.class, () -> {
            this.userService.authenticate(authenticateRequestDTO);
        });

        verify(userRepository, times(1)).findByEmail(authenticateRequestDTO.email());
        verify(passwordEncoder, times(1)).matches(any(String.class), any(String.class));
        verify(jwtService, never()).generateToken(any(String.class));
    }

    @Test
    void should_retrieve_a_user_by_id() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("TEST USER")
                .email("user@test.com")
                .password("userPassword")
                .createdAt(LocalDateTime.now())
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserResponseDTO result = this.userService.getById(user.getId().toString());

        assertNotNull(result);
        verify(userRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void should_throw_an_error_if_user_not_found() {

        UUID nonExistentUser = UUID.randomUUID();

        when(userRepository.findById(nonExistentUser)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            this.userService.getById(nonExistentUser.toString());
        });
    }
}