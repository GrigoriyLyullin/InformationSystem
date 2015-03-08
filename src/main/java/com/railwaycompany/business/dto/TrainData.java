package com.railwaycompany.business.dto;

import java.util.List;

public class TrainData {

    private int id;
    private int number;
    private int seats;
    private List<StationData> stations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<StationData> getStations() {
        return stations;
    }

    public void setStations(List<StationData> stations) {
        this.stations = stations;
    }
}