package com.MarkusE.Wigell_Travel_API.mapper;

import com.MarkusE.Wigell_Travel_API.dto.AddressDto;
import com.MarkusE.Wigell_Travel_API.dto.CreateCustomerDto;
import com.MarkusE.Wigell_Travel_API.dto.CustomerResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Address;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponseDto toResponseDto(Customer customer) {

        Address address = customer.getAddress();

        AddressDto addressDto = new AddressDto(
                address.getStreet(),
                address.getPostalCode(),
                address.getCity()
        );

        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getUserName(),
                addressDto,
                customer.getEmail(),
                customer.getPhone(),
                customer.getDateOfBirth()
        );
    }

    public Customer toEntity(CreateCustomerDto dto, Address address) {
        return new Customer(
                dto.firstName(),
                dto.lastName(),
                dto.userName(),
                address,
                dto.email(),
                dto.phone(),
                dto.dateOfBirth()
        );
    }

    public void updateCustomer(CreateCustomerDto dto, Customer customer, Address address) {
        if (dto.firstName() != null) customer.setFirstName(dto.firstName());
        if (dto.lastName() != null) customer.setLastName(dto.lastName());
        if (dto.userName() != null) customer.setUserName(dto.userName());
        if (dto.email() != null) customer.setEmail(dto.email());
        if (dto.phone() != null) customer.setPhone(dto.phone());
        if (dto.dateOfBirth() != null) customer.setDateOfBirth(dto.dateOfBirth());
        if (address != null) customer.setAddress(address);
    }
}