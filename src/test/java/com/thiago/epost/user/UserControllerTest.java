package com.thiago.epost.user;

import com.thiago.epost.auth.dto.TokenResponseDTO;
import com.thiago.epost.user.dto.AuthenticateRequestDTO;
import com.thiago.epost.user.dto.CreateUserDTO;
import com.thiago.epost.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT )
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    private MockMvc mockMvc;
    private final WebApplicationContext context;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserControllerTest(
            MockMvc mockMvc,
            WebApplicationContext context,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {

        this.mockMvc = mockMvc;
        this.context = context;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    public void setup()
    {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    void should_create_User_e2e() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("TEST User", "test@email.com", "TESTpassword");
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(createUserDTO)
                        )
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_authenticate_user_e2e() throws Exception {
        String passwordHashed = this.passwordEncoder.encode("TESTPassword");
        User user = User.builder().name("TEST User").email("user@test.com").password(passwordHashed).createdAt(LocalDateTime.now()).build();
        this.userRepository.save(user);

        AuthenticateRequestDTO authenticateRequestDTO = new AuthenticateRequestDTO(
                "user@test.com",
                "TESTPassword"
        );


        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJSON(authenticateRequestDTO)
                        )
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void should_get_user_info_e2e() throws Exception {
        User user = User.builder()
                .name("TEST user")
                .email("user@test.com")
                .password("user-test-password")
                .createdAt(LocalDateTime.now())
                .build();

        User userSaved = this.userRepository.save(user);

        var accessToken = TestUtils.generateToken(userSaved.getId().toString(), "aoifghnuiaebgiabgfiawyubfgaiuwgvbaiwugfbawiufgbaiuwfgb").accessToken();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", accessToken)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));;


    }
}