package com.railwaycompany.services;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.StationDao;
import com.railwaycompany.entities.Station;

import java.util.logging.Logger;

public class StationService {

    /**
     * Logger for StationService class.
     */
    private static Logger log = Logger.getLogger(StationService.class.getName());

    private StationDao stationDao;

    public StationService (DaoFactory daoFactory) {
        stationDao = daoFactory.getStationDao();
    }

    public Station getStation(String name) {
        return stationDao.findStation(name);
    }
}
