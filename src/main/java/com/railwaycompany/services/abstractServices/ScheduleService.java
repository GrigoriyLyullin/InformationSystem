package com.railwaycompany.services.abstractServices;

import com.railwaycompany.entities.Station;
import com.railwaycompany.serviceBeans.ScheduleData;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    List<ScheduleData> getSchedule(Station station);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom);

    List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom, Date dateTo);
}
