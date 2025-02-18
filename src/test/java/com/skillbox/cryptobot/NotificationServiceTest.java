package com.skillbox.cryptobot;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private CryptoCurrencyService cryptoCurrencyService;

    @Mock
    private CryptoBot cryptoBot;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    void checkSubscriptions_shouldSendNotification() throws IOException, TelegramApiException {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(123456L);
        subscriber.setPrice(50000.0);

        when(subscriberRepository.findAll()).thenReturn(List.of(subscriber));
        when(cryptoCurrencyService.getBitcoinPrice()).thenReturn(49000.0);

        notificationService.checkSubscriptions();

        verify(cryptoBot, times(1)).execute(any(SendMessage.class));
    }
}