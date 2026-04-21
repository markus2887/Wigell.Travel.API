package com.MarkusE.Wigell_Travel_API.service;

import com.MarkusE.Wigell_Travel_API.dto.CreateDestinationDto;
import com.MarkusE.Wigell_Travel_API.dto.DestinationResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Customer;
import com.MarkusE.Wigell_Travel_API.entity.Destination;
import com.MarkusE.Wigell_Travel_API.mapper.DestinationMapper;
import com.MarkusE.Wigell_Travel_API.repo.AddressRepository;
import com.MarkusE.Wigell_Travel_API.repo.CustomerRepository;
import com.MarkusE.Wigell_Travel_API.repo.DestinationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class DestinationService {

    private static final Logger log = LogManager.getLogger(DestinationService.class);

    private final DestinationRepository destinationRepo;
    private final DestinationMapper destinationMapper;
    private final CurrencyService currencyService;

    public DestinationService(DestinationRepository destinationRepo, DestinationMapper destinationMapper, CurrencyService currencyService) {
        this.destinationRepo = destinationRepo;
        this.destinationMapper = destinationMapper;
        this.currencyService = currencyService;
    }

    @Transactional(readOnly = true)
    public List<DestinationResponseDto> findAll() {

        return destinationRepo.findAll()
                .stream()
                .map(this::toDto)
                .toList();

    }

    @Transactional
    public Destination create(CreateDestinationDto dto) {

        Destination destination = destinationMapper.toEntity(dto);

        log.info("Admin created destination, City={}, Country={}", dto.city(), dto.country());

        return destinationRepo.save(destination);
    }

    public DestinationResponseDto toDto(Destination d) {

        BigDecimal pln = currencyService.convertSekToPln(d.getPriceWeekSek());

        return destinationMapper.toDto(d, pln);
    }

    @Transactional
    public DestinationResponseDto update(Long id, CreateDestinationDto dto) {

        Destination existing = destinationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        existing.setCountry(dto.country());
        existing.setCity(dto.city());
        existing.setHotelName(dto.hotelName());
        existing.setPriceWeekSek(dto.priceWeekSek());

        Destination saved = destinationRepo.save(existing);

        log.info("Updated destinationId={} with country={}, city={}, hotelName={} and priceWeekSek={}", id, dto.country(), dto.city(), dto.hotelName(), dto.priceWeekSek());

        return toDto(saved);
    }

    @Transactional
    public void delete(Long id) {

        Destination destination = destinationRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        log.info("Admin Deleted destination with destinationId={}", id);

        destinationRepo.delete(destination);
    }


}
