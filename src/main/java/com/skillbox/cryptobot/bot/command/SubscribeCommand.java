package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/**
 * Обработка команды подписки на курс валюты
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SubscribeCommand implements IBotCommand {
    private final SubscriberService subscriberService;
    private final CryptoCurrencyService cryptoCurrencyService;

    @Override
    public String getCommandIdentifier() {
        return "subscribe";
    }

    @Override
    public String getDescription() {
        return "Подписывает пользователя на стоимость биткоина";
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        log.info("message message.getChat().getId(): {}", message.getChat().getId());
        log.info("message message.getChat().getUserName(): {}", message.getChat().getUserName());

        if (arguments.length == 0) {
            sendErrorMessage(absSender, message, "Пожалуйста, укажите стоимость биткоина для подписки. /subscribe 95000");
            return;
        }

        try {
            double price = Double.parseDouble(arguments[0]);
            subscriberService.save(message.getChat().getId(), price);

            double currentPrice = cryptoCurrencyService.getBitcoinPrice();
            sendSuccessMessage(absSender, message, currentPrice, price);
        } catch (NumberFormatException e) {
            sendErrorMessage(absSender, message, "Неверный формат стоимости биткоина. Пожалуйста, укажите число.");
        } catch (IOException e) {
            sendErrorMessage(absSender, message, "Ошибка при получении текущей стоимости биткоина.");
        }
    }

    private void sendSuccessMessage(AbsSender absSender, Message message, double currentPrice, double price) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText("Текущая цена биткоина " + TextUtil.toString(currentPrice) + " USD\n" +
                "Новая подписка создана на стоимость " + TextUtil.toString(price) + " USD");
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending success message", e);
        }
    }

    private void sendErrorMessage(AbsSender absSender, Message message, String errorMessage) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());
        answer.setText(errorMessage);
        try {
            absSender.execute(answer);
        } catch (TelegramApiException e) {
            log.error("Error occurred while sending error message", e);
        }
    }

}