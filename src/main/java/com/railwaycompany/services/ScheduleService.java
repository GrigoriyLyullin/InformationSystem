package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.ScheduleDao;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleService {

    public static final String STATION_NAME_ATTR = "Station-Name";

    private ScheduleDao scheduleDao;

    public ScheduleService(DaoFactory daoFactory) {
        scheduleDao = daoFactory.getScheduleDao();
    }

    public List<ScheduleByStation> getScheduleByStationName(String name) {

        List<ScheduleByStation> scheduleByStationList = null;
        Station station = ServiceFactorySingleton.getInstance().getStationService().getStation(name);

        if (station != null) {
            scheduleByStationList = new ArrayList<>();
            List<Schedule> schedulesByStationId = scheduleDao.getSchedules(station.getId());
            if (!schedulesByStationId.isEmpty()) {

                ScheduleByStation scheduleByStation;

                for (Schedule s : schedulesByStationId) {

                    Train train = s.getTrain();

                    scheduleByStation = new ScheduleByStation();
                    scheduleByStation.setTrainId(train.getId());
                    scheduleByStation.setTrainNumber(train.getNumber());
                    scheduleByStation.setTimeArrival(s.getTimeArrival());
                    scheduleByStation.setTimeDeparture(s.getTimeDeparture());

                    scheduleByStationList.add(scheduleByStation);
                }
            }
        }
        return scheduleByStationList;
    }

    public List<ScheduleByStation> getSchedule(String stationFromName, String stationToName, Date dateFrom, Date
            dateTo) {

        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();

        StationService stationService = serviceFactory.getStationService();

        Station stationFrom = stationService.getStation(stationFromName);
        Station stationTo = stationService.getStation(stationToName);

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(dateTo, stationTo.getId());

        List<ScheduleByStation> scheduleByStationList = new ArrayList<>();

        for (Schedule sFrom : schedulesFrom) {
            Train trainFrom = sFrom.getTrain();
            for (Schedule sTo : schedulesTo) {
                Train trainTo = sTo.getTrain();
                if (trainFrom.equals(trainTo)) {
                    ScheduleByStation scheduleByStation = new ScheduleByStation();
                    scheduleByStation.setTrainId(trainFrom.getId());
                    scheduleByStation.setTrainNumber(trainFrom.getNumber());
                    scheduleByStation.setTimeDeparture(sFrom.getTimeDeparture());
                    scheduleByStation.setTimeArrival(sTo.getTimeArrival());
                    scheduleByStationList.add(scheduleByStation);
                }
            }
        }

        return scheduleByStationList;
    }
}
