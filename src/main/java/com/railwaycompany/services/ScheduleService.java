package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.ScheduleDao;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ScheduleService {

    public static final String STATION_NAME_ATTR = "Station-Name";

    private static Logger log = Logger.getLogger(AuthenticationService.class.getName());

    private StationService stationService;

    private ScheduleDao scheduleDao;

    public ScheduleService(DaoFactory daoFactory, StationService stationService) {
        scheduleDao = daoFactory.getScheduleDao();
        this.stationService = stationService;
    }

    public List<ScheduleByStation> getScheduleByStationName(String name) {

        List<ScheduleByStation> scheduleByStationList = null;
        Station station = stationService.getStation(name);

        if (station != null) {
            scheduleByStationList = new ArrayList<>();
            List<Schedule> schedulesByStationId = scheduleDao.getSchedulesByStationId(station.getId());
            if (!schedulesByStationId.isEmpty()) {

                ScheduleByStation scheduleByStation;

                for (Schedule s : schedulesByStationId) {

                    Train train = s.getTrain();

                    scheduleByStation = new ScheduleByStation();
                    scheduleByStation.setTrainNumber(train.getNumber());
                    scheduleByStation.setTimeArrival(s.getTimeArrival());
                    scheduleByStation.setTimeDeparture(s.getTimeDeparture());

                    scheduleByStationList.add(scheduleByStation);
                }
            }
        }
        return scheduleByStationList;
    }
}
