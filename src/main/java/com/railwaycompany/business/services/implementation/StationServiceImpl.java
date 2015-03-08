package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StationServiceImpl implements StationService {

    /**
     * Logger for StationServiceImpl class.
     */
    private static Logger log = Logger.getLogger(StationServiceImpl.class.getName());

    private StationDao stationDao;

    public StationServiceImpl(DaoContext daoContext) {
        stationDao = (StationDao) daoContext.get(StationDao.class);
    }

    @Override
    public Station getStation(String name) {
        return stationDao.getStation(name);
    }

    @Override
    public void addStation(String name) throws StationWithSuchNameExistException {
        Station station = stationDao.getStation(name);
        if (station == null) {
            station = new Station();
            station.setName(name);
            stationDao.create(station);
        } else {
            String msg = "Station \"" + name + "\" already exist!";
            throw new StationWithSuchNameExistException(msg);
        }
    }

    @Override
    public List<StationData> getAll() {
        List<StationData> stationDataList = null;
        List<Station> stationList = stationDao.getAll();
        if (stationList != null && !stationList.isEmpty()) {
            stationDataList = new ArrayList<>();
            for (Station s : stationList) {
                StationData stationData = new StationData();
                stationData.setId(s.getId());
                stationData.setName(s.getName());
                stationDataList.add(stationData);
            }
        }
        return stationDataList;
    }
}
