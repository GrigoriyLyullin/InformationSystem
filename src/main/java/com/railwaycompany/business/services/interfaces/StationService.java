package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.persistence.entities.Station;

import java.util.List;

public interface StationService {

    Station getStation(String name);

    void addStation(String name) throws StationWithSuchNameExistException;

    List<StationData> getAll();
}
