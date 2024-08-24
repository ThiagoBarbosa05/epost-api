package com.thiago.epost.post;

import com.thiago.epost.post.dto.CreatePostDTO;
import com.thiago.epost.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class PostControllerTest {

    private MockMvc mockMvc;
    private final WebApplicationContext context;
    private final PostRepository postRepository;


    @Autowired
    public PostControllerTest(
            MockMvc mockMvc,
            WebApplicationContext context,
            PostRepository postRepository)
    {
        this.mockMvc = mockMvc;
        this.context = context;
        this.postRepository = postRepository;
    }

    @BeforeEach()
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void should_create_post_e2e() throws Exception {
//        CreatePostDTO createPostDTO = new CreatePostDTO("título teste", "conteúdo teste");
//        mockMvc.perform(
//                MockMvcRequestBuilders.post("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(TestUtils.objectToJSON(createPostDTO))
//        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void should_return_posts_with_query_e2e() throws Exception {
//        Post postOne = new Post(UUID.randomUUID(), "Spring Boot", "Spring Boot Content", LocalDateTime.now(), null);
//        Post postTwo = new Post(UUID.randomUUID(), "Spring Data", "Spring Data Content", LocalDateTime.now(), null);
//
//        List<Post> posts = Arrays.asList(postOne, postTwo);
//        Page<Post> expectedPage = new PageImpl<>(posts);
//
//        this.postRepository.saveAll(posts);
//
//        mockMvc.perform(
//                MockMvcRequestBuilders.get("/posts").param("pageNumber", "0").param("pageSize", "5")
//
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//    }
}
}