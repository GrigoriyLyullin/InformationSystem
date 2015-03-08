package com.railwaycompany.business.dto;

import java.util.Date;

public class TicketData {

    private int trainNumber;
    private String stationFrom;
    private String stationTo;
    private Date departureDate;
    private Date arrivalDate;
    private PassengerData passengerData;

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public void setStationFrom(String stationFrom) {
        this.stationFrom = stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public void setStationTo(String stationTo) {
        this.stationTo = stationTo;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public PassengerData getPassengerData() {
        return passengerData;
    }

    public void setPassengerData(PassengerData passengerData) {
        this.passengerData = passengerData;
    }
}
