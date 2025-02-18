package com.skillbox.cryptobot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChat;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@Slf4j
public class CryptoBot extends TelegramLongPollingCommandBot {

    private final String botUsername;

    public CryptoBot(
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.username}") String botUsername,
            List<IBotCommand> commandList
    ) {
        super(botToken);
        log.info("CryptoBot -> constructor -> Bot started. , BotUsername: {}", botUsername);
        this.botUsername = botUsername;

        commandList.forEach(this::register);
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
    }

    public String getUserName(Long telegramId) {
        GetChat getChat = new GetChat();
        getChat.setChatId(telegramId.toString());
        try {
            Chat chat = execute(getChat);
            return chat.getUserName();
        } catch (TelegramApiException e) {
            log.error("Error while getting user info for telegram ID: {}", telegramId, e);
            return null;
        }
    }
}
