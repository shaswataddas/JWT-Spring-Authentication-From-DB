package com.jwt.authentication.jwtAuthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceTrainDTO {
    private int ServiceId;
    private int sourceStationId;
    private int destinationStationId;
    private int distance;
    private double price;
    private String departureTime;
    private String arrivalTime;
    private int trainReference;
    private int trainId;
    private String trainName;
    private int startStationId;
    private  int endStationId;
    private String trainRunOnDays;
    private int bookingId;        // New field
    private LocalDate journeyDate; // New field
    private int totalSeat;        // New field
    private int availableSeat;    // New field
    private int bookedSeat;
}
