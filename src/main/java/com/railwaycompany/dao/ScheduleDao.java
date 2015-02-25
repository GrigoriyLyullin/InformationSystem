package com.railwaycompany.dao;

import com.railwaycompany.entities.Train;

import java.util.List;

public interface ScheduleDao {

    /**
     * Gets the schedule of trains on the station.
     *
     * @param stationName - name of station
     * @return List of trains that go through this station or null if station with this name does not exist (or
     * probably no one train does not go through this station).
     */
    List<Train> getAllTrains(String stationName);
}
