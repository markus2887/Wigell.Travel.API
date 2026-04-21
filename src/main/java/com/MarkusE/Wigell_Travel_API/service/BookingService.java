package com.MarkusE.Wigell_Travel_API.service;

import com.MarkusE.Wigell_Travel_API.dto.BookingResponseDto;
import com.MarkusE.Wigell_Travel_API.dto.CreateBookingDto;
import com.MarkusE.Wigell_Travel_API.dto.UpdateBookingDto;
import com.MarkusE.Wigell_Travel_API.entity.Booking;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import com.MarkusE.Wigell_Travel_API.entity.Destination;
import com.MarkusE.Wigell_Travel_API.mapper.BookingMapper;
import com.MarkusE.Wigell_Travel_API.repo.BookingRepository;
import com.MarkusE.Wigell_Travel_API.repo.CustomerRepository;
import com.MarkusE.Wigell_Travel_API.repo.DestinationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BookingService {

    private static final Logger log = LogManager.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DestinationRepository destinationRepository;
    private final BookingMapper bookingMapper;
    private final CurrencyService currencyService;

    public BookingService(BookingRepository bookingRepository, CustomerRepository customerRepository, DestinationRepository destinationRepository, BookingMapper bookingMapper, CurrencyService currencyService) {
        this.bookingRepository = bookingRepository;
        this.customerRepository = customerRepository;
        this.destinationRepository = destinationRepository;
        this.bookingMapper = bookingMapper;
        this.currencyService = currencyService;
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDto> getBookingsByCustomer(Long customerId) {

        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);

        return bookings.stream()
                .map(bookingMapper::toDto)
                .toList();
    }

    @Transactional
    public BookingResponseDto createBooking(CreateBookingDto dto) {

        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Destination destination = destinationRepository.findById(dto.destinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        // Totalpris SEK
        BigDecimal pricePerWeekSek = destination.getPriceWeekSek();
        BigDecimal totalSek = pricePerWeekSek.multiply(BigDecimal.valueOf(dto.tripDurationWeeks()));

        // Totalpris PLN
        BigDecimal totalPln = currencyService.convertSekToPln(totalSek);

        Booking booking = new Booking(totalSek, totalPln, dto.tripDurationWeeks(), dto.departureDate(), destination, customer);

        Booking saved = bookingRepository.save(booking);

        log.info("Creating booking for customerId={} With destinationId={}", dto.customerId(), dto.destinationId());

        return bookingMapper.toDto(saved);
    }

    @Transactional
    public BookingResponseDto updateBooking(Long bookingId, UpdateBookingDto dto) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (dto.destinationId() != null) {
            Destination destination = destinationRepository.findById(dto.destinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found"));

            booking.setDestination(destination);
        }

        if (dto.tripDurationWeeks() != null) {
            booking.setTripDurationWeeks(dto.tripDurationWeeks());
        }

        BigDecimal pricePerWeek = booking.getDestination().getPriceWeekSek();

        BigDecimal totalSek = pricePerWeek.multiply(BigDecimal.valueOf(booking.getTripDurationWeeks()));

        //Räknar ut pris i PLN med dagens valutakurs. Kund får inte samma kurs som vid bokningen alltså.
        BigDecimal totalPln = currencyService.convertSekToPln(totalSek);

        booking.setTotalPriceSek(totalSek);
        booking.setTotalPricePln(totalPln);

        Booking saved = bookingRepository.save(booking);

        log.info("Updated bookingId={} for customerId={} With destinationId={} and tripDurationWeeks={}", bookingId, booking.getCustomer().getId(), dto.destinationId(), dto.tripDurationWeeks());

        return bookingMapper.toDto(saved);
    }

}
