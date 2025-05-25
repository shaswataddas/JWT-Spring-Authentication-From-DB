package com.jwt.authentication.jwtAuthentication.services;

import com.jwt.authentication.jwtAuthentication.entities.SeatAvailability;
import com.jwt.authentication.jwtAuthentication.repositories.SeatAvailabilityByServiceRepository;
import com.jwt.authentication.jwtAuthentication.repositories.ServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);
    private SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository;
    private ServiceRepository serviceRepository;

    public BookingService(SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository, ServiceRepository serviceRepository) {
        this.seatAvailabilityByServiceRepository = seatAvailabilityByServiceRepository;
        this.serviceRepository = serviceRepository;
    }

    @Transactional
    public ResponseEntity<String> bookTicketsByService(int selectServiceID, LocalDate journeyDate, int noOfSeatsApplied){
        logger.info("Attempting to book {} seats for service ID {} on {}", noOfSeatsApplied, selectServiceID, journeyDate);

        List<Integer> allIntermediaryServices = this.findOtherInterServices(selectServiceID);
        logger.info("All Service IDs are {}",allIntermediaryServices);

        List<SeatAvailability> allBookingRecord = seatAvailabilityByServiceRepository.findByServiceIdInAndJourneyDate(allIntermediaryServices,journeyDate);
        SeatAvailability bookingRecord = seatAvailabilityByServiceRepository.findByServiceIdAndJourneyDate(selectServiceID,journeyDate);

        // Return a 404 Not Found response if the record is not found
        if (allBookingRecord == null) {
            logger.warn("No booking record found for service ID {} on {}", selectServiceID, journeyDate);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No booking record found for the specified service ID and journey date.");
        }

        int totalBookedSeats = bookingRecord.getBookedSeat();
        int totalAvailableSeats = bookingRecord.getAvailableSeat();
//        System.out.println("Total Booked Seat "+totalBookedSeats+"and total Available seat "+totalAvailableSeats);

        if(totalBookedSeats + noOfSeatsApplied <= totalAvailableSeats){
            for(SeatAvailability iterBookingRecord : allBookingRecord){
                iterBookingRecord.setBookedSeat(iterBookingRecord.getBookedSeat() + noOfSeatsApplied);
                iterBookingRecord.setAvailableSeat(iterBookingRecord.getAvailableSeat()-noOfSeatsApplied);
//                System.out.println(iterBookingRecord.getServiceId()+"  "+iterBookingRecord.getBookedSeat()+"   "+iterBookingRecord.getAvailableSeat()+"  "+noOfSeatsApplied);
            }
            seatAvailabilityByServiceRepository.saveAll(allBookingRecord);
            logger.info("Successfully booked {} seats for service ID {} on {}", noOfSeatsApplied, selectServiceID, journeyDate);
            return ResponseEntity.ok("Successfully booked " + noOfSeatsApplied + " seats.");
        }else{
            logger.warn("Insufficient available seats for service ID {} on {}. Requested: {}, Available: {}", selectServiceID, journeyDate, noOfSeatsApplied, totalAvailableSeats);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient available seats. Only " + totalAvailableSeats + " seats are available.");
        }
    }

    private List<Integer> findOtherInterServices(int selectServiceID) {
        List<Integer> serviceIds = new ArrayList<Integer>();
        Optional<com.jwt.authentication.jwtAuthentication.entities.Service> selectedServiceList = serviceRepository.findById(selectServiceID);
        if (selectedServiceList.isPresent()) {
            com.jwt.authentication.jwtAuthentication.entities.Service selectedService  = selectedServiceList.get();
            int sourceStationId =  selectedService.getSourceStationId();
            int destinationStationId = selectedService.getDestinationStationId();
            int trainId = selectedService.getTrainReference();
            List<com.jwt.authentication.jwtAuthentication.entities.Service> services = serviceRepository.findServicesByCriteria(sourceStationId, destinationStationId, trainId);

            serviceIds = services.stream()
                    .map(com.jwt.authentication.jwtAuthentication.entities.Service::getServiceId)
                    .collect(Collectors.toList());
            System.out.println(serviceIds);
        } else {
            System.out.println("No service found");
        }
        return serviceIds;
    }

}
