package com.MarkusE.Wigell_Travel_API.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
public class Booking {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long bookingId;

        @ManyToOne
        @JoinColumn(name = "destination_id")
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private Destination destination;

        @Column(name = "departure", nullable = false)
        private LocalDate departureDate;

        @Column(name = "trip_duration_weeks", nullable = false)
        private int tripDurationWeeks;

        @Column(name = "total_price_sek", nullable = false)
        private BigDecimal totalPriceSek;

        @Column(name = "total_price_pln", nullable = false)
        private BigDecimal totalPricePln;

        @ManyToOne(optional = true, fetch = FetchType.LAZY)
        @JoinColumn(name = "customer_Id")
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        private Customer customer;

        protected Booking() {}

        public Booking(BigDecimal totalPriceSek, BigDecimal totalPricePln, int tripDurationWeeks, LocalDate departureDate, Destination destination, Customer customer) {
            this.totalPriceSek = totalPriceSek;
            this.totalPricePln = totalPricePln;
            this.tripDurationWeeks = tripDurationWeeks;
            this.departureDate = departureDate;
            this.destination = destination;
            this.customer = customer;
        }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDeparture(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public int getTripDurationWeeks() {
        return tripDurationWeeks;
    }

    public void setTripDurationWeeks(int tripDurationWeeks) {
        this.tripDurationWeeks = tripDurationWeeks;
    }

    public BigDecimal getTotalPriceSek() {
        return totalPriceSek;
    }

    public void setTotalPriceSek(BigDecimal totalPriceSek) {
        this.totalPriceSek = totalPriceSek;
    }

    public BigDecimal getTotalPricePln() {
        return totalPricePln;
    }

    public void setTotalPricePln(BigDecimal totalPricePln) {
        this.totalPricePln = totalPricePln;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}

