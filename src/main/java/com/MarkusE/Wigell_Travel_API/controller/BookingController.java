package com.MarkusE.Wigell_Travel_API.controller;

import com.MarkusE.Wigell_Travel_API.dto.BookingResponseDto;
import com.MarkusE.Wigell_Travel_API.dto.CreateBookingDto;
import com.MarkusE.Wigell_Travel_API.dto.UpdateBookingDto;
import com.MarkusE.Wigell_Travel_API.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BookingResponseDto>> getBookings(@RequestParam Long customerId) {

        List<BookingResponseDto> bookings = bookingService.getBookingsByCustomer(customerId);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponseDto> create(@RequestBody CreateBookingDto dto) {

        BookingResponseDto response = bookingService.createBooking(dto);

        URI location = URI.create("/api/v1/bookings/" + response.bookingId());

        return ResponseEntity
                .created(location)
                .body(response);
    }

    @PatchMapping("/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BookingResponseDto> updateBooking(@PathVariable Long bookingId, @RequestBody UpdateBookingDto dto) {

        BookingResponseDto response = bookingService.updateBooking(bookingId, dto);
        return ResponseEntity.ok(response);
    }
}
