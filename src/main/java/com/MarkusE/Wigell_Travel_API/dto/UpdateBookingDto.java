package com.MarkusE.Wigell_Travel_API.dto;

public record UpdateBookingDto(
        Long destinationId,
        Integer tripDurationWeeks
) {}