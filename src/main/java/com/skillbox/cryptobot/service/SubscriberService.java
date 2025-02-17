package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repository.SubscriberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;

    public void save(String name) {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(name);
        Subscriber savedSubscriber = subscriberRepository.save(subscriber);
        log.info("Subscriber saved with UUID: {}, Telegram ID: {}", savedSubscriber.getUuid(), savedSubscriber.getTelegramId());
    }
}
