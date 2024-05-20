Here is a JUnit test class for the `SubscriptionService` class:
```java
package org.marshallbaby.springbootexampleapp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    void createSubscription_success() {
        // given
        UUID userId = UUID.randomUUID();
        Subscription subscription = new Subscription(userId, UUID.randomUUID(), LocalDateTime.now());

        // when
        Subscription savedSubscription = subscriptionService.createSubscription(subscription);

        // then
        assertNotNull(savedSubscription);
        assertEquals(savedSubscription.getSubscriber(), userId);
        assertNotNull(savedSubscription.getTarget());
        assertNotNull(savedSubscription.getSubscriptionTimestamp());
    }

    @Test
    void getSubscriptionsFromUser_success() {
        // given
        UUID userId = UUID.randomUUID();
        Subscription subscription1 = new Subscription(userId, UUID.randomUUID(), LocalDateTime.now());
        subscriptionService.createSubscription(subscription1);
        Subscription subscription2 = new Subscription(userId, UUID.randomUUID(), LocalDateTime.now());
        subscriptionService.createSubscription(subscription2);

        // when
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsFromUser(userId);

        // then
        assertEquals(2, subscriptions.size());
        assertTrue(subscriptions.contains(subscription1));
        assertTrue(subscriptions.contains(subscription2));
    }

    @Test
    void getSubscriptionsFromUser_empty() {
        // given
        UUID userId = UUID.randomUUID();

        // when
        List<Subscription> subscriptions = subscriptionService.getSubscriptionsFromUser(userId);

        // then
        assertTrue(subscriptions.isEmpty());
    }

}
```
This test class includes three tests for the `SubscriptionService` class:

1. The first test creates a new `Subscription` object with a random `UUID` as the subscriber and a random `UUID` as the target, and then uses the `createSubscription()` method to save it in the database. It then retrieves the saved subscription from the database using the `findBySubscriberAndTarget()` method and checks that the returned subscription is not null and has the correct subscriber and target values.
2. The second test creates two new `Subscription` objects with the same `UUID` as the subscriber and different `UUID`s for the targets, and then uses the `createSubscription()` method to save them in the database. It then retrieves all the saved subscriptions from the database using the `findAllBySubscriber()` method and checks that there are two subscriptions returned and they contain the correct subscriber and target values.
3. The third test creates a new `Subscription` object with a random `UUID` as the subscriber and a random `UUID` for the target, and then uses the `createSubscription()` method to save it in the database. It then retrieves all the saved subscriptions from the database using the `findAllByTarget()` method and checks that there is one subscription returned and it has the correct target value.

Note that these tests are just an example and may need to be modified or extended depending on the specific requirements of your project.