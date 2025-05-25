package com.jwt.authentication.jwtAuthentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BookingDetailsRequest {
    @JsonProperty("service_id")
    private int ServiceId;

    @JsonProperty("journey_date")
    private LocalDate journeyDate;

    @JsonProperty("number_of_seat_book")
    private int numberOfSeatBook;

    public int getServiceId() {
        return ServiceId;
    }

    public void setServiceId(int serviceId) {
        ServiceId = serviceId;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public int getNumberOfSeatBook() {
        return numberOfSeatBook;
    }

    public void setNumberOfSeatBook(int numberOfSeatBook) {
        this.numberOfSeatBook = numberOfSeatBook;
    }
}
