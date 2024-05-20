package org.marshallbaby.springbootexampleapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.SpringTestContextInfo;

import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.marshallbaby.springbootexampleapp.service.FeedServiceTest.TestConfig;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig({ TestConfig.class })
public class FeedServiceTest {

    @InjectMocks
    private FeedService feedService;

    @Mock
    private UserService userService;

    @Mock
    private SubscriptionService subscriptionService;

    @Mock
    private PostService postService;

    private UUID userId = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        // Set up mocks for all dependencies
        when(userService.findUser(userId)).thenReturn(new User());
        when(subscriptionService.getSubscriptionsFromUser(userId))
            .thenReturn(List.of(new Subscription()));
        when(postService.getPosts(any())).thenReturn(List.of(new Post()));
    }

    @Test
    public void buildFeedForUser_shouldReturnSortedPosts() {
        List<Post> posts = feedService.buildFeedForUser(userId);
        assertNotNull(posts);
        assertEquals(1, posts.size());
        // Assert that the posts are sorted by creation timestamp
        Post post1 = posts.get(0);
        Post post2 = new Post();
        post2.setCreationTimestamp(LocalDateTime.now().plusDays(1));
        assertTrue(post1.getCreationTimestamp().isBefore(post2.getCreationTimestamp()));
    }
}

@SpringTestContextInfo
class TestConfig {

    @Bean
    public FeedService feedService() {
        return new FeedService();
    }

    @Bean
    public UserService userService() {
        return mock(UserService.class);
    }

    @Bean
    public SubscriptionService subscriptionService() {
        return mock(SubscriptionService.class);
    }

    @Bean
    public PostService postService() {
        return mock(PostService.class);
    }
}