package org.marshallbaby.springbootexampleapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// @JULA:on
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable UUID postId) {

        log.info("Getting post by id: [{}].", postId);
        return postService.findPost(postId);
    }

    @GetMapping
    public List<Post> getPosts(@RequestParam(required = false) UUID authorId) {

        log.info("Getting posts. Author id: [{}].", authorId);
        return postService.getPosts(authorId);
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {

        log.info("Creating post. Author id: [{}].", post.getAuthorId());
        return postService.createPost(post);
    }

}