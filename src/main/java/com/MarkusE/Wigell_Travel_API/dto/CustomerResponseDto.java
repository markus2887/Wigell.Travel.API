package com.MarkusE.Wigell_Travel_API.dto;

import java.time.LocalDate;

public record CustomerResponseDto(
        Long id,
        String firstName,
        String lastName,
        String userName,
        AddressDto address,
        String email,
        String phone,
        LocalDate dateOfBirth
) {}
