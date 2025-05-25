package com.jwt.authentication.jwtAuthentication.controllers;

import com.jwt.authentication.jwtAuthentication.dto.BookingDetailsRequest;
import com.jwt.authentication.jwtAuthentication.dto.ServiceTrainDTO;
import com.jwt.authentication.jwtAuthentication.entities.SeatAvailability;
import com.jwt.authentication.jwtAuthentication.repositories.SeatAvailabilityByServiceRepository;
import com.jwt.authentication.jwtAuthentication.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository;
    private BookingService bookingService;

    public BookingController(SeatAvailabilityByServiceRepository seatAvailabilityByServiceRepository, BookingService bookingService) {
        this.seatAvailabilityByServiceRepository = seatAvailabilityByServiceRepository;
        this.bookingService = bookingService;
    }

    @PostMapping("/service")
    public ResponseEntity<String> bookingService(@RequestBody BookingDetailsRequest bookingDetailsRequest){
        int selectedServiceId = bookingDetailsRequest.getServiceId();
        LocalDate journeyDate = bookingDetailsRequest.getJourneyDate();
        int noOfSeatBook = bookingDetailsRequest.getNumberOfSeatBook();

        ResponseEntity<String> bookingResult = bookingService.bookTicketsByService(selectedServiceId, journeyDate, noOfSeatBook);

        return bookingResult;
    }
}
