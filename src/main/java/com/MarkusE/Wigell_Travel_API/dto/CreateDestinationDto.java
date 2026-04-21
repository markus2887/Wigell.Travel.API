package com.MarkusE.Wigell_Travel_API.dto;

import java.math.BigDecimal;

public record CreateDestinationDto(
        BigDecimal priceWeekSek,
        String hotelName,
        String city,
        String country
) {}
