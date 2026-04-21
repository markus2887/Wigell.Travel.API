package com.MarkusE.Wigell_Travel_API.dto;

import java.time.LocalDate;

public record CreateBookingDto(
        Long customerId,
        Long destinationId,
        int tripDurationWeeks,
        LocalDate departureDate
) {}
