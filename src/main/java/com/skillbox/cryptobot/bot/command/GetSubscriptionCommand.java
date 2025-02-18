package com.skillbox.cryptobot.bot.command;

import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * Команда для получения информации о текущей подписке пользователя.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GetSubscriptionCommand implements IBotCommand {

    private final SubscriberService subscriberService;

    /**
     * Возвращает идентификатор команды.
     *
     * @return Идентификатор команды.
     */
    @Override
    public String getCommandIdentifier() {
        log.info("get_subscription");
        return "get_subscription";
    }

    /**
     * Возвращает описание команды.
     *
     * @return Описание команды.
     */
    @Override
    public String getDescription() {
        return "Возвращает текущую подписку";
    }

    /**
     * Обрабатывает сообщение и возвращает информацию о текущей подписке.
     *
     * @param absSender Отправитель сообщений.
     * @param message Сообщение от пользователя.
     * @param arguments Аргументы команды.
     */
    @Override
    public void processMessage(AbsSender absSender, Message message, String[] arguments) {
        SendMessage answer = new SendMessage();
        answer.setChatId(message.getChatId());

        Long telegramId = message.getChat().getId();
        Double price = subscriberService.getSubscriptionPrice(telegramId);

        if (price != null) {
            answer.setText("Вы подписаны на стоимость биткоина " + TextUtil.toString(price) + " USD");
        } else {
            answer.setText("Активные подписки отсутствуют");
        }

        try {
            absSender.execute(answer);
        } catch (Exception e) {
            log.error("Ошибка возникла в методе /get_subscription", e);
        }
    }
}