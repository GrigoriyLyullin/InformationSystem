package com.railwaycompany.dao.abstractDao;

import com.railwaycompany.entities.Station;

/**
 * GenericDAO<Station> interface for work with Station entities.
 */
public interface StationDao extends GenericDAO<Station> {

    /**
     * Finds Station by station name.
     *
     * @param name - name of the station
     * @return Station entity or null if station with this station name does not exist.
     */
    Station findStation(String name);
}
