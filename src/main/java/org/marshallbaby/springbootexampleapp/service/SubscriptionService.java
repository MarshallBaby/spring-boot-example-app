package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.marshallbaby.springbootexampleapp.repo.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

// @JULA:on
@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final Clock appClock;

    public Subscription createSubscription(Subscription subscription) {

        verifySubscriptionDoesNotAlreadyExists(subscription);
        subscription.setSubscriptionTimestamp(now(appClock));
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getSubscriptionsFromUser(UUID userId) {

        return subscriptionRepository.findAllBySubscriber(userId);
    }

    public List<Subscription> getSubscriptionsToUser(UUID userId) {

        return subscriptionRepository.findAllByTarget(userId);
    }

    public void deleteSubscription(UUID subscriptionId) {

        subscriptionRepository.deleteById(subscriptionId);
    }

    private void verifySubscriptionDoesNotAlreadyExists(Subscription subscription) {

        var existingSubscription = subscriptionRepository.findBySubscriberAndTarget(
                subscription.getSubscriptionId(),
                subscription.getTarget()
        );

        if (existingSubscription.isPresent()) {

            throw new IllegalArgumentException("Subscription already exists.");
        }
    }

}
