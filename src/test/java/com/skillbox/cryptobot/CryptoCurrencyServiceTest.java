package com.skillbox.cryptobot;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CryptoCurrencyServiceTest {
    @Mock
    private BinanceClient binanceClient;

    @InjectMocks
    private CryptoCurrencyService cryptoCurrencyService;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void getBitcoinPrice_shouldReturnCorrectPrice() throws IOException {
        when(binanceClient.getBitcoinPrice()).thenReturn(50000.0);
        double price = cryptoCurrencyService.getBitcoinPrice();
        assertEquals(50000.0, price);
        verify(binanceClient, times(1)).getBitcoinPrice();
    }
}