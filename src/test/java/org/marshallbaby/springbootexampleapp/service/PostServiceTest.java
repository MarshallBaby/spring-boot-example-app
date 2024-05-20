package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    public void createPost_validInputs_success() {
        // given
        Post post = new Post();
        ReflectionTestUtils.setField(post, "text", "Hello World!");
        ReflectionTestUtils.setField(post, "authorId", UUID.randomUUID());

        // when
        postService.createPost(post);

        // then
        verify(postRepository).save(post);
    }

    @Test
    public void findPost_existingPost_success() {
        // given
        UUID postId = UUID.randomUUID();
        Post post = new Post();
        ReflectionTestUtils.setField(post, "text", "Hello World!");
        ReflectionTestUtils.setField(post, "authorId", UUID.randomUUID());
        ReflectionTestUtils.setField(post, "creationTimestamp", LocalDateTime.now());
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        Post result = postService.findPost(postId);

        // then
        assertEquals(post, result);
    }

    @Test
    public void findPost_nonExistingPost_throwsException() {
        // given
        UUID postId = UUID.randomUUID();
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        // when
        assertThrows(NotFoundException.class, () -> postService.findPost(postId));
    }

    @Test
    public void getAllPosts_existingPosts_success() {
        // given
        UUID authorId = UUID.randomUUID();
        Post post1 = new Post();
        ReflectionTestUtils.setField(post1, "text", "Hello World!");
        ReflectionTestUtils.setField(post1, "authorId", authorId);
        ReflectionTestUtils.setField(post1, "creationTimestamp", LocalDateTime.now());
        Post post2 = new Post();
        ReflectionTestUtils.setField(post2, "text", "Goodbye World!");
        ReflectionTestUtils.setField(post2, "authorId", authorId);
        ReflectionTestUtils.setField(post2, "creationTimestamp", LocalDateTime.now());
        given(postRepository.findAllByAuthorId(authorId)).willReturn(List.of(post1, post2));

        // when
        List<Post> result = postService.getAllPosts(authorId);

        // then
        assertEquals(List.of(post1, post2), result);
    }
}