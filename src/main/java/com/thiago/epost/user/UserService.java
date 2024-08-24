package com.thiago.epost.user;

import com.thiago.epost.auth.JwtService;
import com.thiago.epost.auth.dto.TokenResponseDTO;
import com.thiago.epost.exceptions.InvalidCredentialsException;
import com.thiago.epost.exceptions.ResourceNotFoundException;
import com.thiago.epost.exceptions.UserAlreadyExistsException;
import com.thiago.epost.user.dto.AuthenticateRequestDTO;
import com.thiago.epost.user.dto.CreateUserDTO;
import com.thiago.epost.user.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public User createUser(CreateUserDTO createUserDTO) {
        Optional<User> userAlreadyExists = this.userRepository.findByEmail(createUserDTO.email());

        if(userAlreadyExists.isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String passwordHashed = passwordEncoder.encode(createUserDTO.password());

        LocalDateTime createdAt = LocalDateTime.now();

        User user = User.builder()
                .name(createUserDTO.name())
                .email(createUserDTO.email())
                .password(passwordHashed)
                .createdAt(createdAt).build();

        return this.userRepository.save(user);
    }

    public TokenResponseDTO authenticate(AuthenticateRequestDTO authenticateRequestDTO) {
        Optional<User> user = this.userRepository.findByEmail(authenticateRequestDTO.email());

        if(user.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        String userPasswordHashed = user.get().getPassword();

        boolean doesPasswordMatches = this.passwordEncoder.matches(
                authenticateRequestDTO.password(),
                userPasswordHashed
        );

        if(!doesPasswordMatches) {
            throw new InvalidCredentialsException();
        }

        String userId = user.get().getId().toString();

        return this.jwtService.generateToken(userId);
    }

    public UserResponseDTO getById(String userId) {
        UUID userIdUUID = UUID.fromString(userId);
        Optional<User> userFromDatabase = this.userRepository.findById(userIdUUID);

        if (userFromDatabase.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado.");
        }

        User user = userFromDatabase.get();

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getProfileImage(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
