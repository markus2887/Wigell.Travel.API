package com.MarkusE.Wigell_Travel_API.controller;

import com.MarkusE.Wigell_Travel_API.dto.AddressDto;
import com.MarkusE.Wigell_Travel_API.dto.AddressResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Address;
import com.MarkusE.Wigell_Travel_API.mapper.AddressMapper;
import com.MarkusE.Wigell_Travel_API.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/customers/{customerId}/addresses")
public class AddressController {

    private final CustomerService service;

    public AddressController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AddressResponseDto> create(@PathVariable Long customerId, @RequestBody AddressDto dto) {

        AddressResponseDto saved = service.saveAddress(customerId, dto);

        URI location = URI.create("/api/v1/customers/" + customerId + "/addresses/" + saved.addressId());

        return ResponseEntity
                .created(location)
                .body(saved);
    }

    @DeleteMapping("/{addressId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeAddress(@PathVariable Long customerId, @PathVariable Long addressId) {
        service.removeAddressFromCustomer(customerId, addressId);

        return ResponseEntity.noContent().build();
    }


}