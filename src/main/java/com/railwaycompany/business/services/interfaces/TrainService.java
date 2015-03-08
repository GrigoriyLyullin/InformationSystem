package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;

import java.util.List;

public interface TrainService {

    List<TrainData> getAll();

    void addTrain(int trainNumber, int trainSeats, boolean addAnyway) throws TrainWithSuchNumberExistException;
}
