package org.marshallbaby.springbootexampleapp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class FeedServiceTest {
    private final UserService userService = Mockito.mock(UserService.class);
    private final SubscriptionService subscriptionService = Mockito.mock(SubscriptionService.class);
    private final PostService postService = Mockito.mock(PostService.class);
    private final FeedService feedService = new FeedService(userService, subscriptionService, postService);

    @Test
    public void buildFeedForUser_validUserId_shouldReturnListOfPosts() {
        UUID userId = UUID.randomUUID();
        List<Subscription> subscriptions = Mockito.mock(List.class);
        Mockito.when(subscriptionService.getSubscriptionsFromUser(userId)).thenReturn(subscriptions);
        List<Post> posts = Mockito.mock(List.class);
        Mockito.when(postService.getPosts(Mockito.any())).thenReturn(posts);
        List<Post> result = feedService.buildFeedForUser(userId);
        assertEquals(subscriptions, result);
    }

    @Test
    public void buildFeedForUser_invalidUserId_shouldThrowException() {
        UUID userId = null;
        Assertions.assertThrows(IllegalArgumentException.class, () -> feedService.buildFeedForUser(userId));
    }
}