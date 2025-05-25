package com.jwt.authentication.jwtAuthentication.repositories;

import com.jwt.authentication.jwtAuthentication.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service,Integer> {

    @Query("SELECT s FROM Service s WHERE s.sourceStationId = :fromStationId AND s.destinationStationId = :toStationId")
    List<Service> findServicesByStations(@Param("fromStationId") int fromStationId, @Param("toStationId") int toStationId);

    @Query("SELECT s FROM Service s WHERE (s.sourceStationId < :bookingDestination AND s.destinationStationId > :bookingSource) AND s.trainReference = :trainReference")
    List<Service> findServicesByCriteria(@Param("bookingSource") int bookingSource,
                                         @Param("bookingDestination") int bookingDestination,
                                         @Param("trainReference") int trainReference);
}
