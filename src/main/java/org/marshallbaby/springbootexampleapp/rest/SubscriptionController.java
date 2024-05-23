package org.marshallbaby.springbootexampleapp.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.marshallbaby.springbootexampleapp.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

// @JULA:on
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/from/{userId}")
    public List<Subscription> getSubscriptionsFromUser(@PathVariable UUID userId) {

        log.info("Getting subscriptions FROM user: [{}].", userId);
        return subscriptionService.getSubscriptionsFromUser(userId);
    }

    @GetMapping("/to/{userId}")
    public List<Subscription> getSubscriptionsToUser(@PathVariable UUID userId) {

        log.info("Getting subscriptions TO user: [{}].", userId);
        return subscriptionService.getSubscriptionsToUser(userId);
    }

    @PostMapping
    public Subscription createSubscription(@RequestBody Subscription subscription) {

        log.info("Creating subscription from [{}] to [{}].",
                subscription.getSubscriber(),
                subscription.getTarget());
        return subscriptionService.createSubscription(subscription);
    }

    @DeleteMapping("/{subscriptionId}")
    public void deleteSubscription(@PathVariable UUID subscriptionId) {

        log.info("Deleting subscription: [{}].", subscriptionId);
        subscriptionService.deleteSubscription(subscriptionId);
    }

}
