package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDao stationDao;

    public StationServiceImpl() {

    }

    @Override
    public StationData getStation(String name) {
        Station station = stationDao.getStation(name);
        StationData stationData = null;
        if (station != null) {
            stationData = new StationData();
            stationData.setId(station.getId());
            stationData.setName(station.getName());
        }
        return stationData;
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

    @Override
    public List<String> getAllStationNames(String startWithStr) {
        List<String> stationNameList = new ArrayList<>();
        List<Station> stationList = stationDao.getAll();
        if (startWithStr == null || startWithStr.isEmpty()) {
            for (Station station : stationList) {
                stationNameList.add(station.getName());
            }
        } else {
            for (Station station : stationList) {
                String name = station.getName();
                if (name.startsWith(startWithStr.toUpperCase()) || name.startsWith(startWithStr.toLowerCase())) {
                    stationNameList.add(name);
                }
            }
        }
        return stationNameList;
    }
}
