package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Station;

import java.util.List;

/**
 * GenericDAO<Station> interface for work with Station entities.
 */
public interface StationDao extends GenericDAO<Station> {

    /**
     * Finds Station by station name.
     *
     * @param name - name of the station
     * @return Station entity or null if station with this station name does not exist
     */
    Station getStation(String name);

    List<Station> getAll();
}
