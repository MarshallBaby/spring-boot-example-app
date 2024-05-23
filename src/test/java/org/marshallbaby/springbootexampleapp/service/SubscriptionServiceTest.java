package org.marshallbaby.springbootexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@SpringJUnitConfig(classes = {SubscriptionService.class})
@RequiredArgsConstructor
public class SubscriptionServiceTest {
    @Autowired
    private SubscriptionService subscriptionService;

    @MockBean
    private SubscriptionRepository subscriptionRepository;

    @Test
    @Transactional
    public void testCreateSubscription() {
        // given
        var subscription = new Subscription();
        subscription.setSubscriber(UUID.randomUUID());
        subscription.setTarget(UUID.randomUUID());

        // when
        var result = subscriptionService.createSubscription(subscription);

        // then
        assertThat(result).isEqualTo(1);
    }
}