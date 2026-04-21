package com.MarkusE.Wigell_Travel_API.controller;

import com.MarkusE.Wigell_Travel_API.dto.CreateCustomerDto;
import com.MarkusE.Wigell_Travel_API.dto.CustomerResponseDto;
import com.MarkusE.Wigell_Travel_API.service.CustomerService;
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

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
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

        CustomerResponseDto saved = customerService.create(dto);

        URI location = URI.create("/api/v1/customers/" + saved.id());

        return ResponseEntity
                .created(location)
                .body(saved);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CustomerResponseDto> update(@PathVariable Long id, @RequestBody CreateCustomerDto dto) {

        CustomerResponseDto response = customerService.update(id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}