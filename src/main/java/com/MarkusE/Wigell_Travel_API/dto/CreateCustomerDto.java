package com.MarkusE.Wigell_Travel_API.dto;

import java.time.LocalDate;

public record CreateCustomerDto(
        String firstName,
        String lastName,
        String userName,
        Long addressId,
        String email,
        String phone,
        LocalDate dateOfBirth
) {}
