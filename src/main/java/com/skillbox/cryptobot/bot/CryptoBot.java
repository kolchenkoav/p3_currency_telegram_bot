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

/**
 * Сервис для работы с Telegram-ботом, который обрабатывает команды и сообщения.
 */
@Service
@Slf4j
public class CryptoBot extends TelegramLongPollingCommandBot {

    private final String botUsername;

    /**
     * Конструктор для создания экземпляра CryptoBot.
     *
     * @param botToken токен бота.
     * @param botUsername имя пользователя бота.
     * @param commandList список команд бота.
     */
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

    /**
     * Возвращает имя пользователя бота.
     *
     * @return имя пользователя бота.
     */
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    /**
     * Обрабатывает обновления, которые не являются командами.
     *
     * @param update обновление от Telegram.
     */
    @Override
    public void processNonCommandUpdate(Update update) {
    }

    /**
     * Получает имя пользователя по его Telegram ID.
     *
     * @param telegramId Telegram ID пользователя.
     * @return имя пользователя или null в случае ошибки.
     */
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
