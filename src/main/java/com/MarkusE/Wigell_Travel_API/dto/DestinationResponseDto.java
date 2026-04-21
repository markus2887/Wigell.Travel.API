package com.MarkusE.Wigell_Travel_API.dto;

import java.math.BigDecimal;

public record DestinationResponseDto(
        Long destinationId,
        String country,
        String city,
        String hotelName,
        BigDecimal priceWeekSek,
        BigDecimal priceWeekPln
) {}