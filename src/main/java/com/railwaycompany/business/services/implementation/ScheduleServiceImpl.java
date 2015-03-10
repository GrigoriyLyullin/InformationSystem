package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.exceptions.StationDoesNotExistException;
import com.railwaycompany.business.services.exceptions.SuchScheduleExistException;
import com.railwaycompany.business.services.exceptions.TrainDoesNotExistException;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {


    private ScheduleDao scheduleDao;

    private StationDao stationDao;

    private TrainDao trainDao;

    public ScheduleServiceImpl(DaoContext daoContext) {
        scheduleDao = (ScheduleDao) daoContext.get(ScheduleDao.class);
        stationDao = (StationDao) daoContext.get(StationDao.class);
        trainDao = (TrainDao) daoContext.get(TrainDao.class);
    }

    @Override
    public List<ScheduleData> getSchedule(Station station) {

        List<ScheduleData> scheduleDataList = null;

        if (station != null) {
            List<Schedule> schedulesByStationId = scheduleDao.getSchedulesByStationId(station.getId());
            if (schedulesByStationId != null) {
                scheduleDataList = new ArrayList<>();
                if (!schedulesByStationId.isEmpty()) {
                    for (Schedule s : schedulesByStationId) {
                        Train train = s.getTrain();
                        ScheduleData scheduleData = new ScheduleData();
                        scheduleData.setTrainId(train.getId());
                        scheduleData.setTrainNumber(train.getNumber());
                        scheduleData.setTimeArrival(s.getTimeArrival());
                        scheduleData.setTimeDeparture(s.getTimeDeparture());
                        scheduleDataList.add(scheduleData);
                    }
                }
            }
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationName) {
        Station station = stationDao.getStation(stationName);
        return getSchedule(station);
    }

    @Override
    public List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom, Date
            dateTo) {

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(dateTo, stationTo.getId());

        List<ScheduleData> scheduleDataList = null;

        if (schedulesFrom != null && schedulesTo != null && dateFrom != null && dateTo != null && !schedulesFrom
                .isEmpty() && !schedulesTo.isEmpty()) {
            scheduleDataList = new ArrayList<>();
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
        }
        return scheduleDataList;
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom) {
        Station stationFrom = stationDao.getStation(stationFromName);
        Station stationTo = stationDao.getStation(stationToName);
        return getSchedule(stationFrom, stationTo, dateFrom);
    }

    @Override
    public List<ScheduleData> getSchedule(String stationFromName, String stationToName, Date dateFrom, Date dateTo) {
        Station stationFrom = stationDao.getStation(stationFromName);
        Station stationTo = stationDao.getStation(stationToName);
        return getSchedule(stationFrom, stationTo, dateFrom, dateTo);
    }

    @Override
    public void addSchedule(int stationId, int trainId, Date arrivalDate, Date departureDate)
            throws SuchScheduleExistException, TrainDoesNotExistException, StationDoesNotExistException {
        Station station = stationDao.read(stationId);
        if (station != null) {
            Train train = trainDao.read(trainId);
            if (train != null) {
                Schedule schedule = scheduleDao.getSchedule(stationId, trainId, arrivalDate, departureDate);
                if (schedule == null) {
                    Schedule newSchedule = new Schedule();
                    newSchedule.setStation(station);
                    newSchedule.setTrain(train);
                    newSchedule.setTimeArrival(arrivalDate);
                    newSchedule.setTimeDeparture(departureDate);
                    scheduleDao.create(newSchedule);
                } else {
                    throw new SuchScheduleExistException();
                }
            } else {
                throw new TrainDoesNotExistException();
            }
        } else {
            throw new StationDoesNotExistException();
        }
    }

    @Override
    public List<ScheduleData> getSchedule(Station stationFrom, Station stationTo, Date dateFrom) {

        List<Schedule> schedulesFrom = scheduleDao.getSchedules(stationFrom.getId(), dateFrom);
        List<Schedule> schedulesTo = scheduleDao.getSchedules(stationTo.getId(), dateFrom);

        List<ScheduleData> scheduleDataList = null;

        if (schedulesFrom != null && schedulesTo != null && dateFrom != null && !schedulesFrom.isEmpty() &&
                !schedulesTo.isEmpty()) {

            scheduleDataList = new ArrayList<>();

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
        }
        return scheduleDataList;
    }
}
