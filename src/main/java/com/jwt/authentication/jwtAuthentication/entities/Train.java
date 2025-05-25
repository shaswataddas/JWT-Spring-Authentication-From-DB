package com.jwt.authentication.jwtAuthentication.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "trains")
public class Train {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private int trainId;

    @Column(name = "train_name")
    private String trainName;

    @Column(name = "start_station_id")
    private int sourceStationId;

    @Column(name = "end_station_id")
    private  int destinationStationId;

    @Column(name = "run_on_days")
    private String runOnDays;
}
