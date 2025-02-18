package com.skillbox.cryptobot;

import com.skillbox.cryptobot.client.BinanceClient;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CryptoCurrencyServiceTest {
    @Mock
    private BinanceClient binanceClient;

    @InjectMocks
    private CryptoCurrencyService cryptoCurrencyService;

    @Test
    void getBitcoinPrice_shouldReturnCorrectPrice() throws IOException {
        when(binanceClient.getBitcoinPrice()).thenReturn(50000.0);
        double price = cryptoCurrencyService.getBitcoinPrice();
        assertEquals(50000.0, price);
        verify(binanceClient, times(1)).getBitcoinPrice();
    }
}