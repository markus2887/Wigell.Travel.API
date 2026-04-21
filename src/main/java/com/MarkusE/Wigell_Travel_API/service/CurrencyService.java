package com.MarkusE.Wigell_Travel_API.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CurrencyService {

    private final BigDecimal SEK_TO_PLN_RATE = new BigDecimal("2.5641");

    public BigDecimal convertSekToPln(BigDecimal sek) {

        return sek.divide(SEK_TO_PLN_RATE, 2, RoundingMode.HALF_UP);
    }
}
