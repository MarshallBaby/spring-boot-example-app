package org.marshallbaby.springbootexampleapp.service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.marshallbaby.springbootexampleapp.service.FeedServiceTest.TestUserService;
import org.marshallbaby.springbootexampleapp.service.FeedServiceTest.TestPostService;
import org.marshallbaby.springbootexampleapp.service.FeedServiceTest.TestSubscriptionService;
import static java.util.Comparator.comparing;

@ExtendWith(MockitoExtension.class)
public class FeedServiceTest {

    @InjectMocks
    private FeedService feedService;

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private SubscriptionService subscriptionService;

    private UUID testUserId = new UUID(0, 1);
    private List<Post> testPosts;
    private List<Subscription> testSubscriptions;

    @BeforeEach
    public void setUp() {
        testPosts = List.of(new Post("test post", testUserId, LocalDateTime.now()), new Post("another test post", testUserId, LocalDateTime.now()));
        testSubscriptions = List.of(new Subscription(testUserId, UUID.randomUUID(), LocalDateTime.now()), new Subscription(testUserId, UUID.randomUUID(), LocalDateTime.now()));
    }

    @Test
    public void buildFeedForUser_validInputs_success() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(testSubscriptions);
        when(postService.getPosts(any(UUID.class))).thenReturn(testPosts);

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertEquals(2, feed.size());
        assertTrue(feed.containsAll(testPosts));
    }

    @Test
    public void buildFeedForUser_invalidInputs_throwsException() {
        when(userService.findUser(testUserId)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> feedService.buildFeedForUser(testUserId));
    }

    @Test
    public void buildFeedForUser_subscriptionWithNoPosts_returnsEmptyList() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(List.of());

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertTrue(feed.isEmpty());
    }

    @Test
    public void buildFeedForUser_postWithNoAuthor_returnsEmptyList() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(testSubscriptions);
        testPosts = List.of(new Post("test post", null, LocalDateTime.now()), new Post("another test post", null, LocalDateTime.now()));
        when(postService.getPosts(any(UUID.class))).thenReturn(testPosts);

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertTrue(feed.isEmpty());
    }

    @Test
    public void buildFeedForUser_subscriptionWithNoAuthor_returnsEmptyList() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        testSubscriptions = List.of(new Subscription(null, UUID.randomUUID(), LocalDateTime.now()), new Subscription(null, UUID.randomUUID(), LocalDateTime.now()));
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(testSubscriptions);

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertTrue(feed.isEmpty());
    }

    @Test
    public void buildFeedForUser_postWithNoCreationTimestamp_returnsEmptyList() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(testSubscriptions);
        testPosts = List.of(new Post("test post", null, LocalDateTime.now()), new Post("another test post", null, LocalDateTime.now()));
        when(postService.getPosts(any(UUID.class))).thenReturn(testPosts);

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertTrue(feed.isEmpty());
    }

    @Test
    public void buildFeedForUser_subscriptionWithNoCreationTimestamp_returnsEmptyList() {
        when(userService.findUser(testUserId)).thenReturn(new TestUser());
        testSubscriptions = List.of(new Subscription(null, UUID.randomUUID(), null), new Subscription(null, UUID.randomUUID(), LocalDateTime.now()));
        when(subscriptionService.getSubscriptionsFromUser(testUserId)).thenReturn(testSubscriptions);

        List<Post> feed = feedService.buildFeedForUser(testUserId);

        assertTrue(feed.isEmpty());
    }

    private class TestUser {

        public UUID getId() {
            return testUserId;
        }

        public String getName() {
            return "Test User";
        }

    }

    @Data
    private static class TestUserService implements UserService {

        @Override
        public User findUser(UUID id) {
            if (id.equals(testUserId)) {
                return new TestUser();
            } else {
                return null;
            }
        }
    }

    @Data
    private static class TestPostService implements PostService {

        @Override
        public List<Post> getPosts(UUID authorId) {
            if (authorId.equals(testUserId)) {
                return testPosts;
            } else {
                return null;
            }
        }
    }

    private static class TestSubscriptionService implements SubscriptionService {

        @Override
        public List<Subscription> getSubscriptionsForUser(UUID userId) {
            if (userId.equals(testUserId)) {
                return testSubscriptions;
            } else {
                return null;
            }
        }
    }
}