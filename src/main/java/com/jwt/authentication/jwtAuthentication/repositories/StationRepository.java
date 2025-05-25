package com.jwt.authentication.jwtAuthentication.repositories;

import com.jwt.authentication.jwtAuthentication.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station,Integer> {
    Station findByStationName(String stationName);
}
