package com.thiago.epost.user;

import com.thiago.epost.auth.dto.TokenResponseDTO;
import com.thiago.epost.exceptions.dto.ResponseErrorDTO;
import com.thiago.epost.user.dto.AuthenticateRequestDTO;
import com.thiago.epost.user.dto.CreateUserDTO;

import com.thiago.epost.user.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Tag(name ="User")
    @Operation(summary = "Registrar um novo usuário", description = "Essa rota é responsável em registrar um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
            this.userService.createUser(createUserDTO);
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.CREATED);
    }

    @Tag(name = "User")
    @Operation(summary = "Login do usuário", description = "Essa rota é responsável em realizar o login do usuário e retornar um token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TokenResponseDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "401", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseErrorDTO.class)
                    )
            })
    })
    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody AuthenticateRequestDTO authenticateRequestDTO) {
        TokenResponseDTO accessToken = this.userService.authenticate(authenticateRequestDTO);

        return new ResponseEntity<>(accessToken, new HttpHeaders(), HttpStatus.OK);
    }

    @Tag(name = "User")
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getProfile(HttpServletRequest request) {
        Object userId = request.getAttribute("user_id");

        UserResponseDTO user = this.userService.getById(userId.toString());

        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }
}
