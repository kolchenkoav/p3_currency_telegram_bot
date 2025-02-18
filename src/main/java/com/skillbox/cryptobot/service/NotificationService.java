package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import com.skillbox.cryptobot.bot.CryptoBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationService {

    private final SubscriberRepository subscriberRepository;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CryptoBot cryptoBot;

    @Value("${telegram.bot.notify.delay.value}")
    private int notifyDelayValue;

    @Value("${telegram.bot.notify.delay.unit}")
    private String notifyDelayUnit;

    @Value("${telegram.bot.notify.frequency.value}")
    private int notifyFrequencyValue;

    @Value("${telegram.bot.notify.frequency.unit}")
    private String notifyFrequencyUnit;

    private final Map<Long, Instant> lastNotificationTime = new HashMap<>();

    @Scheduled(fixedDelayString = "${telegram.bot.notify.frequency.value}", timeUnit = TimeUnit.MINUTES)
    public void checkSubscriptions() {
        try {
            double currentPrice = cryptoCurrencyService.getBitcoinPrice();
            List<Subscriber> subscribers = subscriberRepository.findAll();

            for (Subscriber subscriber : subscribers) {
                if (subscriber.getPrice() != null && currentPrice >= subscriber.getPrice()) {
                    Long telegramId = subscriber.getTelegramId();
                    Instant lastNotification = lastNotificationTime.get(telegramId);
                    Instant now = Instant.now();

                    if (lastNotification == null || Duration.between(lastNotification, now).compareTo(Duration.ofMinutes(notifyDelayValue)) >= 0) {
                        sendNotification(telegramId, currentPrice);
                        lastNotificationTime.put(telegramId, now);
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error while checking subscriptions", e);
        }
    }

    private void sendNotification(Long telegramId, double currentPrice) {
        SendMessage message = new SendMessage();
        message.setChatId(telegramId.toString());
        message.setText("Пора покупать, стоимость биткоина " + currentPrice + " USD");

        try {
            cryptoBot.execute(message);
        } catch (TelegramApiException e) {
            log.error("Error while sending notification to user {}", telegramId, e);
        }
    }
}