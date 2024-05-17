package org.marshallbaby.springbootexampleapp.repo;

import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    List<Subscription> findAllBySubscriber(UUID subscriber);

    List<Subscription> findAllByTarget(UUID target);

    Optional<Subscription> findBySubscriberAndTarget(UUID subscriber, UUID target);

}
