package com.skillbox.cryptobot;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.service.SubscriberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceTest {
    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberService subscriberService;

    private final Long telegramId = 123456L;

    @Test
    void save_shouldCreateOrUpdateSubscriber() {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(telegramId);
        subscriber.setPrice(50000.0);

        when(subscriberRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(subscriber));
        when(subscriberRepository.save(any(Subscriber.class))).thenAnswer(invocation -> invocation.getArgument(0));

        subscriberService.save(telegramId, 45000.0);

        assertEquals(45000.0, subscriber.getPrice());
    }

    @Test
    void unsubscribe_shouldRemoveSubscriber() {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(telegramId);

        when(subscriberRepository.findByTelegramId(telegramId)).thenReturn(Optional.of(subscriber));
        boolean unsubscribed = subscriberService.unsubscribe(telegramId);
        assertTrue(unsubscribed);
        verify(subscriberRepository, times(1)).delete(subscriber);
    }
}
