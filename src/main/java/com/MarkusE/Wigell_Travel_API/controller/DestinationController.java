package com.MarkusE.Wigell_Travel_API.controller;

import com.MarkusE.Wigell_Travel_API.dto.CreateDestinationDto;
import com.MarkusE.Wigell_Travel_API.dto.DestinationResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Destination;
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
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
        public List<DestinationResponseDto> getAll() {
        return destinationService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DestinationResponseDto> create(@RequestBody CreateDestinationDto dto) {

        Destination saved = destinationService.create(dto);

        URI location = URI.create("/api/v1/destinations/" + saved.getDestinationId());

        return ResponseEntity
                .created(location)
                .body(destinationService.toDto(saved));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DestinationResponseDto> update(@PathVariable Long id, @RequestBody CreateDestinationDto dto) {

        DestinationResponseDto updated = destinationService.update(id, dto);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        destinationService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
