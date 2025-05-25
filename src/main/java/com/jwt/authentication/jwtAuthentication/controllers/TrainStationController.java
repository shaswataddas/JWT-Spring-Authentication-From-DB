package com.jwt.authentication.jwtAuthentication.controllers;


import com.jwt.authentication.jwtAuthentication.dto.SearchTrainRequest;
import com.jwt.authentication.jwtAuthentication.dto.ServiceTrainDTO;
import com.jwt.authentication.jwtAuthentication.entities.Station;
import com.jwt.authentication.jwtAuthentication.entities.Train;
import com.jwt.authentication.jwtAuthentication.services.SeatAvailabilityService;
import com.jwt.authentication.jwtAuthentication.services.StationService;
import com.jwt.authentication.jwtAuthentication.services.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
public class TrainStationController {

    private TrainService trainService;
    private StationService stationService;
    private SeatAvailabilityService seatAvailabilityService;

    public TrainStationController(TrainService trainService, StationService stationService, SeatAvailabilityService seatAvailabilityService) {
        this.trainService = trainService;
        this.stationService = stationService;
        this.seatAvailabilityService = seatAvailabilityService;
    }

    @GetMapping("/searchTrain/{trainName}")
    public ResponseEntity<Integer> findTrainByName(@PathVariable String trainName){
        try{
            Train theTrain = trainService.findByTrainName(trainName);
            if(theTrain != null) {
                return ResponseEntity.ok(theTrain.getTrainId());
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch(Exception e) {
            System.err.println("Error fetching station: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/searchStation/{stationName}")
    public ResponseEntity<Integer> findStationIdByName(@PathVariable String stationName) {
        try {
            Station theStation = stationService.findByStationName(stationName);
            if (theStation != null) {
                return ResponseEntity.ok(theStation.getStationId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }
        } catch (Exception e) {
            System.err.println("Error fetching station: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PostMapping("/searchTrainAvailability")
    public ResponseEntity<List<ServiceTrainDTO>> findServiceByStationName(@RequestBody SearchTrainRequest searchTrainRequest){
        String sourceStation = searchTrainRequest.getSourceStation();
        String destinationStation = searchTrainRequest.getDestinationStation();
        LocalDate journeyDate = searchTrainRequest.getJourneyDate();

        ResponseEntity<List<ServiceTrainDTO>> resultList = seatAvailabilityService.findAvailableTrainServices(sourceStation, destinationStation,journeyDate);

        return resultList;
    }


}
