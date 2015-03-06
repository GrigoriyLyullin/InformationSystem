package com.railwaycompany.services.abstractServices;

import com.railwaycompany.serviceBeans.ScheduleData;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    List<ScheduleData> getSchedule(String name);

    List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom, Date dateTo);

    List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom);
}
