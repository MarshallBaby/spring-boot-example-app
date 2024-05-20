package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.exception.NotFoundException;
import org.marshallbaby.springbootexampleapp.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;

// @JULA:on
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final Clock appClock;

    public Post createPost(Post post) {

        post.setCreationTimestamp(now(appClock));
        return postRepository.save(post);
    }

    public Post findPost(UUID postId) {

        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(format("Could not find post with id: [%s].", postId)));
    }

    public List<Post> getPosts(UUID authorId) {

        return Objects.isNull(authorId) ?
                postRepository.findAll() :
                postRepository.findAllByAuthorId(authorId);
    }

}
