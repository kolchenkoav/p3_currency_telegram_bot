package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Сервис для работы с криптовалютами.
 */
@Slf4j
@Service
public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClient client;

    /**
     * Конструктор сервиса.
     *
     * @param client клиент для взаимодействия с Binance.
     */
    public CryptoCurrencyService(BinanceClient client) {
        this.client = client;
    }

    /**
     * Получает текущую цену биткоина.
     *
     * @return текущая цена биткоина.
     * @throws IOException если возникла ошибка при получении цены.
     */
    public double getBitcoinPrice() throws IOException {
        if (price.get() == null) {
            price.set(client.getBitcoinPrice());
        }
        return price.get();
    }
}
