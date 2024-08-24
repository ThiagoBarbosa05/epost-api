package com.thiago.epost.post;

import com.thiago.epost.exceptions.dto.ResponseErrorDTO;
import com.thiago.epost.post.dto.CreatePostDTO;
import com.thiago.epost.post.dto.PageMetadataDTO;
import com.thiago.epost.post.dto.PagedPostResponseDTO;
import com.thiago.epost.post.dto.PostResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    public PostService postService;

    @Tag(name = "Post")
    @Operation(summary = "Criação de um post", description = "Essa rota é responsável pela criação de um post.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))}),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseErrorDTO.class))})
    })
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid @RequestBody CreatePostDTO createPostDTO) {

        var result = this.postService.createPost(createPostDTO);

        return ResponseEntity.status(201).body(result);
    }

    @Tag(name = "Post")
    @Operation(summary = "Busca de posts com paginação e filtragem por título ou conteúdo", description = "Essa rota é responsável pela listagem de posts")
    @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PagedPostResponseDTO.class))})
    @GetMapping
    public ResponseEntity<Object> getAllPosts(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String query)
    {
        var posts = postService.getAllPosts(query, pageNumber, pageSize);
        List<PostResponseDTO> postResponseDTO = posts.getContent()
                .stream()
                .map(post -> new PostResponseDTO(
                   post.getId(),
                   post.getTitle(),
                   post.getContent(),
                   post.getCreatedAt(),
                   post.getUpdatedAt()
                )).toList();

        PageMetadataDTO pageMetadataDTO =  new PageMetadataDTO(
                posts.getSize(),
                posts.getNumber(),
                posts.getTotalElements(),
                posts.getTotalPages()
        );

        PagedPostResponseDTO postsToHttpResponse = new PagedPostResponseDTO(postResponseDTO, pageMetadataDTO);

        return  new ResponseEntity<>(postsToHttpResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
