package com.MarkusE.Wigell_Travel_API.mapper;

import com.MarkusE.Wigell_Travel_API.dto.AddressResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressResponseDto toDto(Address address) {
        return new AddressResponseDto(
                address.getAddressId(),
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );
    }

}
