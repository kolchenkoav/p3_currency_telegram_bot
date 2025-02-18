package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscriberService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Обработка команды отмены подписки на курс валюты
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UnsubscribeCommand implements IBotCommand {

    private final SubscriberService subscriberService;

    @Override
    public String getCommandIdentifier() {
        return "unsubscribe";
    }

    @Override
    public String getDescription() {
        return "Отменяет подписку пользователя";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        Long telegramId = message.getChat().getId();
        boolean unsubscribed = subscriberService.unsubscribe(telegramId);

        if (unsubscribed) {
            answer.setText("Подписка отменена");
        } else {
            answer.setText("Активные подписки отсутствуют");
        }

        try {
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("Ошибка возникла в методе /unsubscribe", e);
        }
    }
}