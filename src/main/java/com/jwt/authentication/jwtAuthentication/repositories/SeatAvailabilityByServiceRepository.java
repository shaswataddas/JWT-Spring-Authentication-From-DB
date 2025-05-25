package com.jwt.authentication.jwtAuthentication.repositories;


import com.jwt.authentication.jwtAuthentication.entities.SeatAvailability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SeatAvailabilityByServiceRepository extends JpaRepository<SeatAvailability,Integer> {
    List<SeatAvailability> findByServiceIdInAndJourneyDate(List<Integer> serviceIds, LocalDate journeyDate);
    SeatAvailability findByServiceIdAndJourneyDate(Integer serviceId, LocalDate journeyDate);
}
