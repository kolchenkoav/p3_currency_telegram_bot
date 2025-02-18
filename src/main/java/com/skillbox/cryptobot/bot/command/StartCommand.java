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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

/**
 * Обработка команды начала работы с ботом
 */

@Slf4j
@AllArgsConstructor
@Service
public class StartCommand implements IBotCommand {
    private final SubscriberService subscriberService;

    @Override
    public String getCommandIdentifier() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Запускает бота";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        answer.setText("""
                Привет! Данный бот помогает отслеживать стоимость биткоина.
                Поддерживаемые команды:
                 /subscribe [число] - подписаться на рассылку стоимости биткоина в USD
                 /get_price - получить стоимость биткоина
                 /get_subscriptions - получить текущую подписку
                 /unsubscribe - отменить подписку на рассылку стоимости биткоина
                """);

        log.info("message message.getChat().getId(): {}", message.getChat().getId());
        subscriberService.save(message.getChat().getId(), null);
        log.info("message message.getChat().getFirstName(): {}", message.getChat().getFirstName());
        log.info("message message.getChat().getLastName(): {}", message.getChat().getLastName());
        log.info("message message.getChat().getUserName(): {}", message.getChat().getUserName());

        log.info("arguments: {}", Arrays.toString(arguments));
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred in /start command", e);
        }
    }
}