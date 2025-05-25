package com.jwt.authentication.jwtAuthentication.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class SearchTrainRequest {

    @JsonProperty("sourceStation")
    private String sourceStation;

    @JsonProperty("destinationStation")
    private String destinationStation;

    @JsonProperty("journeyDate")
    private LocalDate journeyDate;

    public String getSourceStation() {
        return sourceStation;
    }

    public void setSourceStation(String sourceStation) {
        this.sourceStation = sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }
}
