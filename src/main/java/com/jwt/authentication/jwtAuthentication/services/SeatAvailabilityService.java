package com.jwt.authentication.jwtAuthentication.services;

import com.jwt.authentication.jwtAuthentication.dto.ServiceTrainDTO;
import com.jwt.authentication.jwtAuthentication.entities.SeatAvailability;
import com.jwt.authentication.jwtAuthentication.entities.Station;
import com.jwt.authentication.jwtAuthentication.entities.Train;
import com.jwt.authentication.jwtAuthentication.repositories.SeatAvailabilityByServiceRepository;
import com.jwt.authentication.jwtAuthentication.repositories.ServiceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatAvailabilityService {

    private ServiceRepository serviceRepository;
    private StationService stationService;
    private TrainService trainService;
    private SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository;

    public SeatAvailabilityService(ServiceRepository serviceRepository, StationService stationService, TrainService trainService, SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository) {
        this.serviceRepository = serviceRepository;
        this.stationService = stationService;
        this.trainService = trainService;
        this.seatAvailabilityByServiceRepository = seatAvailabilityByServiceRepository;
    }

    public List<SeatAvailability> findAvailableSeatService(List<Integer> serviceIdList, LocalDate journeyDate){
        List<SeatAvailability> seatAvailabilityList = seatAvailabilityByServiceRepository.findByServiceIdInAndJourneyDate(serviceIdList, journeyDate);
        System.out.println(seatAvailabilityList);
        return seatAvailabilityList;
    }

    public ResponseEntity<List<ServiceTrainDTO>> findAvailableTrainServices(String sourceStationName, String destinationStationName, LocalDate journeyDate){
        Station sourceStation = stationService.findByStationName(sourceStationName);
        Station destinationStation = stationService.findByStationName(destinationStationName);

        List<com.jwt.authentication.jwtAuthentication.entities.Service> serviceList = serviceRepository.findServicesByStations(sourceStation.getStationId(), destinationStation.getStationId());

        if (serviceList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }

        List<Integer> trainReferences = serviceList.stream()
                .map(com.jwt.authentication.jwtAuthentication.entities.Service::getTrainReference)
                .collect(Collectors.toList());

        List<Integer> serviceReferences = serviceList.stream()
                .map(com.jwt.authentication.jwtAuthentication.entities.Service::getServiceId)
                .collect(Collectors.toList());

        List<Train> trainDetails = trainService.findByTrainId(trainReferences);

        List<SeatAvailability> seatAvailabilityList = this.findAvailableSeatService(serviceReferences, journeyDate);

        // Merge Service and Train data
        List<ServiceTrainDTO> result = serviceList.stream()
                .flatMap(service -> trainDetails.stream()
                        .filter(train -> train.getTrainId() == service.getTrainReference())
                        .flatMap(train -> seatAvailabilityList.stream()
                                .filter(seat -> seat.getServiceId() == service.getServiceId())
                                .map(seat -> {
                                    ServiceTrainDTO dto = new ServiceTrainDTO();
                                    dto.setServiceId(service.getServiceId());
                                    dto.setSourceStationId(service.getSourceStationId());
                                    dto.setDestinationStationId(service.getDestinationStationId());
                                    dto.setDistance(service.getDistance());
                                    dto.setPrice(service.getPrice());
                                    dto.setDepartureTime(service.getDepartureTime());
                                    dto.setArrivalTime(service.getArrivalTime());
                                    dto.setTrainReference(service.getTrainReference());
                                    dto.setTrainId(train.getTrainId());
                                    dto.setTrainName(train.getTrainName());
                                    dto.setStartStationId(train.getSourceStationId());
                                    dto.setEndStationId(train.getDestinationStationId());
                                    dto.setTrainRunOnDays(train.getRunOnDays());
                                    dto.setBookingId(seat.getBookingId());
                                    dto.setJourneyDate(seat.getJourneyDate());
                                    dto.setTotalSeat(seat.getTotalSeat());
                                    dto.setAvailableSeat(seat.getAvailableSeat());
                                    dto.setBookedSeat(seat.getBookedSeat());
                                    return dto;
                                })
                        )
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }



}
