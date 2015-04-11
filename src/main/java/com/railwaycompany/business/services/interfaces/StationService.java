package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;

import java.util.List;

public interface StationService {

    StationData getStation(String name);

    void addStation(String name) throws StationWithSuchNameExistException;

    List<StationData> getAll();

    List<String> getAllStationNames(String startWith);

    boolean exist(String stationName);
}
