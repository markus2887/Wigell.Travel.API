package com.MarkusE.Wigell_Travel_API.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "destinations")
public class Destination {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long destinationId;

        @Column(name = "price_week_sek", nullable = false)
        private BigDecimal priceWeekSek;

        @Column(name = "hotel_name", length = 30, nullable = false)
        private String hotelName;

        @Column(name = "city", length = 30, nullable = false)
        private String city;

        @Column(name = "country", length = 30, nullable = false)
        private String country;


        protected Destination() {}

        public Destination(BigDecimal priceWeekSek, String hotelName, String city, String country) {
            this.priceWeekSek = priceWeekSek;
            this.hotelName = hotelName;
            this.city = city;
            this.country = country;
        }

    public Long getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    public BigDecimal getPriceWeekSek() {
        return priceWeekSek;
    }

    public void setPriceWeekSek(BigDecimal priceWeekSek) {
        this.priceWeekSek = priceWeekSek;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
