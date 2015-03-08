package com.railwaycompany.business.services.interfaces;

import java.util.Date;

public interface TrainService {

    boolean hasEmptySeats(int trainId);

    Date getDepartureDate(String stationFromName, int trainId);

    Integer getTrainId(int trainNumber);
}
