package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Comparator.comparing;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final PostService postService;

    public List<Post> buildFeedForUser(UUID userId) {

        validateUserExists(userId);

        return subscriptionService.getSubscriptionsFromUser(userId)
                .parallelStream()
                .map(Subscription::getTarget)
                .flatMap(author -> postService.getPosts(author).stream())
                .sorted(comparing(Post::getCreationTimestamp))
                .toList();
    }

    private void validateUserExists(UUID userId) {

        userService.findUser(userId);
    }

}