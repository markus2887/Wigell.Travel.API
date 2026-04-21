package com.MarkusE.Wigell_Travel_API.controller;

import com.MarkusE.Wigell_Travel_API.dto.BookingResponseDto;
import com.MarkusE.Wigell_Travel_API.dto.CreateCustomerDto;
import com.MarkusE.Wigell_Travel_API.dto.CustomerResponseDto;
import com.MarkusE.Wigell_Travel_API.dto.DestinationResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Address;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import com.MarkusE.Wigell_Travel_API.mapper.CustomerMapper;
import com.MarkusE.Wigell_Travel_API.mapper.DestinationMapper;
import com.MarkusE.Wigell_Travel_API.service.CustomerService;
import com.MarkusE.Wigell_Travel_API.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CustomerResponseDto>> getAllCustomers() {

        List<CustomerResponseDto> customers = customerService.findAll();

        return ResponseEntity.ok(customers);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> create(@RequestBody CreateCustomerDto dto) {

        Address address = customerService.getAddress(dto.addressId());
        Customer customer = customerMapper.toEntity(dto, address);

        CustomerResponseDto saved = customerService.save(customer);

        //CustomerResponseDto responseDto = customerMapper.toResponseDto(saved);

        URI location = URI.create("/api/v1/customers/" + saved.id());

        return ResponseEntity
                .created(location)
                .body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable Long id, @RequestBody CreateCustomerDto dto) {

        Customer existing = customerService.findById(id);
        Address address = customerService.getAddress(dto.addressId());

        customerMapper.updateCustomer(dto, existing, address);

        CustomerResponseDto response = customerService.save(existing);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}