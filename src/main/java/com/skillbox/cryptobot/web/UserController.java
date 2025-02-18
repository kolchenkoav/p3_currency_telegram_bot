package com.skillbox.cryptobot.web;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.bot.CryptoBot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final CryptoBot cryptoBot;
    private final SubscriberService subscriberService;

    @GetMapping
    public List<String> getUsers() {
        List<Subscriber> subscribers = subscriberService.getAllSubscribers();
        return subscribers.stream()
                .map(subscriber -> {
                    String userName = cryptoBot.getUserName(subscriber.getTelegramId());
                    return String.format("%d, %s, %.3f", subscriber.getTelegramId(), userName, subscriber.getPrice());
                })
                .collect(Collectors.toList());
    }
}
