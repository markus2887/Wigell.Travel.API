package com.MarkusE.Wigell_Travel_API.dto;

import com.MarkusE.Wigell_Travel_API.entity.Address;

public record AddressResponseDto(
            Long addressId,
            String street,
            String postalCode,
            String city
) {}
