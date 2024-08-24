package com.thiago.epost.post;

import com.thiago.epost.post.dto.CreatePostDTO;
import com.thiago.epost.post.dto.PagedPostResponseDTO;
import com.thiago.epost.post.dto.PostResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {


    private final PostRepository postRepository;

    @Autowired
    public  PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(CreatePostDTO createPostDTO) {
        Post post = new Post();
        post.setTitle(createPostDTO.title());
        post.setContent(createPostDTO.content());

        LocalDateTime postCreatedAt = LocalDateTime.now();
        post.setCreatedAt(postCreatedAt);

        return this.postRepository.save(post);
    }

    public Page<Post> getAllPosts(String query, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        if(query != null) {
            return  this.postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query, pageable);

        }

        return this.postRepository.findAll(pageable);
    }
}
