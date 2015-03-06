package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.ScheduleDao;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;
import com.railwaycompany.serviceBeans.ScheduleData;
import com.railwaycompany.services.abstractServices.ScheduleService;
import com.railwaycompany.services.abstractServices.ServiceFactory;
import com.railwaycompany.services.abstractServices.StationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    public static final String STATION_NAME_ATTR = "Station-Name";

    private ScheduleDao scheduleDao;

    public ScheduleServiceImpl(DaoFactory daoFactory) {
        scheduleDao = daoFactory.getScheduleDao();
    }

    @Override
    public List<ScheduleData> getSchedule(String name) {

        List<ScheduleData> scheduleDataList = null;
        Station station = ServiceFactorySingleton.getInstance().getStationService().getStation(name);

        if (station != null) {
            scheduleDataList = new ArrayList<>();
            List<Schedule> schedulesByStationId = scheduleDao.getSchedules(station.getId());
            if (!schedulesByStationId.isEmpty()) {

                ScheduleData scheduleData;

                for (Schedule s : schedulesByStationId) {

                    Train train = s.getTrain();

                    scheduleData = new ScheduleData();
                    scheduleData.setTrainId(train.getId());
                    scheduleData.setTrainNumber(train.getNumber());
                    scheduleData.setTimeArrival(s.getTimeArrival());
                    scheduleData.setTimeDeparture(s.getTimeDeparture());

                    scheduleDataList.add(scheduleData);
                }
            }
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom, Date
            dateTo) {

        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();

        StationService stationService = serviceFactory.getStationService();

        Station stationFrom = stationService.getStation(stationFromName);
        Station stationTo = stationService.getStation(stationToName);

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(dateTo, stationTo.getId());

        List<ScheduleData> scheduleDataList = new ArrayList<>();

        for (Schedule sFrom : schedulesFrom) {
            Train trainFrom = sFrom.getTrain();
            for (Schedule sTo : schedulesTo) {
                Train trainTo = sTo.getTrain();
                if (trainFrom.equals(trainTo)) {
                    ScheduleData scheduleData = new ScheduleData();
                    scheduleData.setTrainId(trainFrom.getId());
                    scheduleData.setTrainNumber(trainFrom.getNumber());
                    scheduleData.setTimeDeparture(sFrom.getTimeDeparture());
                    scheduleData.setTimeArrival(sTo.getTimeArrival());
                    scheduleDataList.add(scheduleData);
                }
            }
        }

        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom) {

        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();

        StationService stationService = serviceFactory.getStationService();

        Station stationFrom = stationService.getStation(stationFromName);
        Station stationTo = stationService.getStation(stationToName);

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(stationTo.getId(), dateFrom);

        List<ScheduleData> scheduleDataList = new ArrayList<>();

        for (Schedule sFrom : schedulesFrom) {
            Train trainFrom = sFrom.getTrain();
            for (Schedule sTo : schedulesTo) {
                Train trainTo = sTo.getTrain();
                if (trainFrom.equals(trainTo)) {
                    ScheduleData scheduleData = new ScheduleData();
                    scheduleData.setTrainId(trainFrom.getId());
                    scheduleData.setTrainNumber(trainFrom.getNumber());
                    scheduleData.setTimeDeparture(sTo.getTimeDeparture());
                    scheduleData.setTimeArrival(sFrom.getTimeArrival());
                    scheduleDataList.add(scheduleData);
                }
            }
        }

        return scheduleDataList;
    }
}
