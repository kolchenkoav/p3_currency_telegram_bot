package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public void save(Long id, Double price) {
        Optional<Subscriber> existingSubscriber = subscriberRepository.findByTelegramId(id);
        Subscriber subscriber;
        if (existingSubscriber.isPresent()) {
            subscriber = existingSubscriber.get();
            subscriber.setPrice(price);
        } else {
            subscriber = new Subscriber();
            subscriber.setTelegramId(id);
            subscriber.setPrice(price);
        }
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        log.info("Subscriber saved with UUID: {}, Telegram ID: {}, Price: {}", savedSubscriber.getUuid(), savedSubscriber.getTelegramId(), savedSubscriber.getPrice());
    }

    public Double getSubscriptionPrice(Long telegramId) {
        log.info("Getting subscription price for telegram ID: {}", telegramId);
        Optional<Subscriber> subscriber = subscriberRepository.findByTelegramId(telegramId);
        return subscriber.map(Subscriber::getPrice).orElse(null);
    }

    public boolean unsubscribe(Long telegramId) {
        Optional<Subscriber> subscriber = subscriberRepository.findByTelegramId(telegramId);
        if (subscriber.isPresent()) {
            subscriberRepository.delete(subscriber.get());
            log.info("Subscriber with Telegram ID: {} unsubscribed", telegramId);
            return true;
        } else {
            log.info("No active subscription found for Telegram ID: {}", telegramId);
            return false;
        }
    }
}
