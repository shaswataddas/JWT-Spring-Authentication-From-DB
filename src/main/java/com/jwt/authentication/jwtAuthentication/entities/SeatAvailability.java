package com.jwt.authentication.jwtAuthentication.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "seat_availability")
public class SeatAvailability {
    @Id
    @Column(name = "booking_id")
    private int bookingId;

    @Column(name = "journey_date")
    private LocalDate journeyDate;

    @Column(name = "service_id")
    private int serviceId;

    @Column(name = "total_seat")
    private int totalSeat;

    @Column(name = "available_seat")
    private int availableSeat;

    @Column(name = "booked_seat")
    private int bookedSeat;
}
