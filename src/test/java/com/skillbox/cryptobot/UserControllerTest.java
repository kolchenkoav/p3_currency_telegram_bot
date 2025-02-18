package com.skillbox.cryptobot;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.web.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private CryptoBot cryptoBot;

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private UserController userController;

    @Test
    void getUsers_shouldReturnUserList() {
        Subscriber subscriber = new Subscriber();
        subscriber.setTelegramId(123456L);
        subscriber.setPrice(50000.0);

        when(subscriberService.getAllSubscribers()).thenReturn(List.of(subscriber));
        when(cryptoBot.getUserName(123456L)).thenReturn("test_user");

        List<String> users = userController.getUsers();

        assertFalse(users.isEmpty());
        assertEquals("123456, test_user, 50000.000", users.get(0));
    }
}