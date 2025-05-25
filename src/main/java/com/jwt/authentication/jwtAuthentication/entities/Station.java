package com.jwt.authentication.jwtAuthentication.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "station")
public class Station {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "station_id")
    private int stationId;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "station_location")
    private String stationLocation;

    @Column(name = "station_code")
    private String stationCode;
}
