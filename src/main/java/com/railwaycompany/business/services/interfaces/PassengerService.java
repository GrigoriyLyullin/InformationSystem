package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.PassengerData;

import java.util.List;

public interface PassengerService {

    List<PassengerData> getAllPassengersByTrainId(int trainId);
}
