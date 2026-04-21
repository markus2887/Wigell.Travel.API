package com.MarkusE.Wigell_Travel_API.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponseDto(
        Long bookingId,
        Long customerId,
        String hotelName,
        String city,
        String country,
        int tripDurationWeeks,
        LocalDate departureDate,
        BigDecimal totalPriceSek,
        BigDecimal totalPricePln
) {}
