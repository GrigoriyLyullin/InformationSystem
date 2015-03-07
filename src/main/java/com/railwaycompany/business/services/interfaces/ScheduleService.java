package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.business.dto.ScheduleData;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    List<ScheduleData> getSchedule(Station station);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom, Date dateTo);
}
