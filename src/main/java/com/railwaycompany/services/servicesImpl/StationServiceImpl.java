package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.StationDao;
import com.railwaycompany.entities.Station;
import com.railwaycompany.services.abstractServices.StationService;

import java.util.logging.Logger;

public class StationServiceImpl implements StationService {

    /**
     * Logger for StationServiceImpl class.
     */
    private static Logger log = Logger.getLogger(StationServiceImpl.class.getName());

    private StationDao stationDao;

    public StationServiceImpl(DaoFactory daoFactory) {
        stationDao = daoFactory.getStationDao();
    }

    @Override
    public Station getStation(String name) {
        return stationDao.findStation(name);
    }
}
