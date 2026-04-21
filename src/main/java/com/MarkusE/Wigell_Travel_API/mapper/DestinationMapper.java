package com.MarkusE.Wigell_Travel_API.mapper;

import com.MarkusE.Wigell_Travel_API.dto.CreateDestinationDto;
import com.MarkusE.Wigell_Travel_API.dto.DestinationResponseDto;
import com.MarkusE.Wigell_Travel_API.entity.Destination;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class DestinationMapper {


    public DestinationResponseDto toDto(Destination d, BigDecimal pln) {
        return new DestinationResponseDto(
                d.getDestinationId(),
                d.getCountry(),
                d.getCity(),
                d.getHotelName(),
                d.getPriceWeekSek(),
                pln
        );
    }

        public Destination toEntity(CreateDestinationDto dto) {
            return new Destination(
            dto.priceWeekSek(),
            dto.hotelName(),
            dto.city(),
            dto.country());
        }
}
