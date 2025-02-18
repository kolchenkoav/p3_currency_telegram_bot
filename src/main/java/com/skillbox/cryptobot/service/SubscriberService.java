package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления подписчиками.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    /**
     * Сохраняет подписчика с указанным Telegram ID и ценой.
     *
     * @param id    Telegram ID подписчика.
     * @param price Цена подписки.
     */
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

    /**
     * Получает цену подписки для указанного Telegram ID.
     *
     * @param telegramId Telegram ID подписчика.
     * @return Цена подписки или null, если подписчик не найден.
     */
    public Double getSubscriptionPrice(Long telegramId) {
        log.info("Getting subscription price for telegram ID: {}", telegramId);
        Optional<Subscriber> subscriber = subscriberRepository.findByTelegramId(telegramId);
        return subscriber.map(Subscriber::getPrice).orElse(null);
    }

    /**
     * Отменяет подписку для указанного Telegram ID.
     *
     * @param telegramId Telegram ID подписчика.
     * @return true, если подписка была отменена, иначе false.
     */
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

    /**
     * Получает список всех подписчиков.
     *
     * @return Список всех подписчиков.
     */
    public List<Subscriber> getAllSubscribers() {
        return subscriberRepository.findAll().stream().toList();
    }
}
