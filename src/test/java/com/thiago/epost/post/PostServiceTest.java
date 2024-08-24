package com.thiago.epost.post;

import com.thiago.epost.post.dto.CreatePostDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void should_to_create_a_post() {
//        CreatePostDTO createPostDTO = new CreatePostDTO("Titulo test", "conteúdo teste");
//        Post post = new Post();
//
//        when(postRepository.save(any(Post.class))).thenReturn(post);
//
//        var result = postService.createPost(createPostDTO);
//
//        assertNotNull(result);
//        assertEquals(result, post);
    }

    @Test
    void should_get_posts_with_query() {
//        String query = "spring";
//        int pageNumber = 0;
//        int pageSize = 5;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        Post postOne = new Post(UUID.randomUUID(), "Spring Boot", "Spring Boot Content", LocalDateTime.now(), null);
//        Post postTwo = new Post(UUID.randomUUID(), "Spring Data", "Spring Data Content", LocalDateTime.now(), null);
//
//        List<Post> posts = Arrays.asList(postOne, postTwo);
//        Page<Post> expectedPage = new PageImpl<>(posts);
//
//        when(postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query, pageable)).thenReturn(expectedPage);
//
//        Page<Post> result = this.postService.getAllPosts(query, pageNumber, pageSize);
//
//        assertNotNull(result);
//        assertEquals(2, result.getTotalElements());
//        verify(postRepository, times(1)).findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query, pageable);
    }

    @Test
    void should_get_posts_without_query() {
//        int pageNumber = 0;
//        int pageSize = 5;
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//
//        Post postOne = new Post(UUID.randomUUID(), "Spring Security", "Spring Security Content", LocalDateTime.now(), null);
//        Post postTwo = new Post(UUID.randomUUID(), "Spring Cloud", "Spring Cloud Content", LocalDateTime.now(), null);
//
//        List<Post> posts = Arrays.asList(postOne, postTwo);
//        Page<Post> expectedPage = new PageImpl<>(posts);
//
//        when(postRepository.findAll(pageable)).thenReturn(expectedPage);
//
//        Page<Post> result = this.postService.getAllPosts(null, pageNumber, pageSize);
//
//        assertNotNull(result);
//        assertEquals(2, result.getTotalElements());
//        verify(postRepository, times(1)).findAll(pageable);
    }
}