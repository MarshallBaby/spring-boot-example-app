package org.marshallbaby.springbootexampleapp.repo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.springbootexampleapp.domain.Post;
import org.marshallbaby.springbootexampleapp.service.FeedService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    @GetMapping("/{userId}")
    public List<Post> buildFeed(@PathVariable UUID userId) {

        log.info("Building feed for user: [{}].", userId);
        return feedService.buildFeedForUser(userId);
    }

}
