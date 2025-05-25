package com.jwt.authentication.jwtAuthentication.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "service")
public class Service {
    @Id
    @Column(name = "service_id")
    private int ServiceId;

    @Column(name = "from_station_id")
    private int sourceStationId;

    @Column(name = "to_station_id")
    private int destinationStationId;

    @Column(name = "distance")
    private int distance;

    @Column(name = "price")
    private double price;

    @Column(name = "departure_time")
    private String departureTime;

    @Column(name = "arrival_time")
    private String arrivalTime;

    @Column(name = "train_reference")
    private int trainReference;
}
