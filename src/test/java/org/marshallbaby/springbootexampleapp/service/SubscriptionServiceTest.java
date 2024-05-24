Here is an example of a test class for the provided Java class:
```
package org.marshallbaby.springbootexampleapp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.marshallbaby.springbootexampleapp.domain.Subscription;
import org.marshallbaby.springbootexampleapp.repo.SubscriptionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class SubscriptionServiceTest {

    @Autowired
    private SubscriptionService subscriptionService;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Test
    public void createSubscription_validInputs_success() {
        // Arrange
        UUID subscriber = UUID.randomUUID();
        UUID target = UUID.randomUUID();
        LocalDateTime subscriptionTimestamp = LocalDateTime.now();

        Subscription expectedSubscription = new Subscription(subscriber, target, subscriptionTimestamp);

        // Act
        Subscription actualSubscription = subscriptionService.createSubscription(subscriber, target);

        // Assert
        assertEquals(expectedSubscription, actualSubscription);
    }
}
```
Note that this is just an example and you may need to modify it based on your specific requirements. Additionally, you will need to mock the dependencies of the `SubscriptionService` class in order to test its methods without making any real calls to the database.