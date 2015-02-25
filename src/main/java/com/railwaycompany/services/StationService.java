package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;
import com.railwaycompany.dao.StationDao;

import java.util.logging.Logger;

public class StationService {

    /**
     * Logger for StationService class.
     */
    private static Logger log = Logger.getLogger(StationService.class.getName());

    private DaoFactory daoFactory;

    private StationDao stationDao;

    public StationService () {
        daoFactory = HibernateDaoFactorySingleton.getInstance();
        stationDao = daoFactory.getStationDao();
    }
}
