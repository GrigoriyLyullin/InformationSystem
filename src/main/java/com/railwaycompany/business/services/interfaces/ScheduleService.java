package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.exceptions.StationDoesNotExistException;
import com.railwaycompany.business.services.exceptions.SuchScheduleExistException;
import com.railwaycompany.business.services.exceptions.TrainDoesNotExistException;
import com.railwaycompany.persistence.entities.Station;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    List<ScheduleData> getSchedule(Station station);

    List<ScheduleData> getSchedule(String stationName);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom, Date dateTo);

    List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom);

    List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom, Date dateTo);

    void addSchedule(int stationId, int trainId, Date arrivalDate, Date departureDate) throws
            SuchScheduleExistException, StationDoesNotExistException, TrainDoesNotExistException;
}
