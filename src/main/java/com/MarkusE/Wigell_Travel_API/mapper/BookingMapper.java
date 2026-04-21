package com.MarkusE.Wigell_Travel_API.mapper;

import com.MarkusE.Wigell_Travel_API.dto.BookingResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Booking;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BookingMapper {

    public BookingResponseDto toDto(Booking booking) {
        return new BookingResponseDto(
                booking.getBookingId(),
                booking.getCustomer().getId(),
                booking.getDestination().getHotelName(),
                booking.getDestination().getCity(),
                booking.getDestination().getCountry(),
                booking.getTripDurationWeeks(),
                booking.getDepartureDate(),
                booking.getTotalPriceSek(),
                booking.getTotalPricePln()
        );
    }
}
