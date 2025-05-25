package com.jwt.authentication.jwtAuthentication.services;

import com.jwt.authentication.jwtAuthentication.entities.Station;
import com.jwt.authentication.jwtAuthentication.repositories.StationRepository;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    private StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public Station findByStationName(String stationName){
        return stationRepository.findByStationName(stationName);
    }
}
