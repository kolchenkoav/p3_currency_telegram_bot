package com.skillbox.cryptobot;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@Transactional
class NotificationServiceIntegrationTest {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private CryptoCurrencyService cryptoCurrencyService;

    @MockBean
    private CryptoBot cryptoBot; // Теперь это мок-объект

    @Test
    void checkSubscriptions_shouldTriggerNotification() throws TelegramApiException {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(123456L);
        subscriber.setPrice(50000.0);
        subscriberRepository.save(subscriber);

        notificationService.checkSubscriptions();

        verify(cryptoBot, atLeastOnce()).execute(any(SendMessage.class)); // Добавили явное указание типа
    }
}
