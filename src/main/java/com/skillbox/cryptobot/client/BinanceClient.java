package com.skillbox.cryptobot.client;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Клиент для взаимодействия с API Binance.
 */
@Slf4j
@Service
public class BinanceClient {
    private final HttpGet httpGet;
    private final ObjectMapper mapper;
    private final HttpClient httpClient;

    /**
     * Конструктор клиента Binance.
     *
     * @param uri URI для получения цены биткоина.
     */
    public BinanceClient(@Value("${binance.api.getPrice}") String uri) {
        httpGet = new HttpGet(uri);
        mapper = new ObjectMapper();
        httpClient = HttpClientBuilder.create()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
    }

    /**
     * Получает текущую цену биткоина.
     *
     * @return Цена биткоина в виде double.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    public double getBitcoinPrice() throws IOException {
        log.info("Выполняется вызов клиента к binanceApi для получения цены биткоина");
        try {
            return mapper.readTree(EntityUtils.toString(httpClient.execute(httpGet).getEntity()))
                    .path("price").asDouble();
        } catch (IOException e) {
            log.error("Ошибка при получении цены с binance", e);
            throw e;
        }
    }
}
