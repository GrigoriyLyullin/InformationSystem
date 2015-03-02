package com.railwaycompany.services;

import com.railwaycompany.dao.*;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class TrainService {

    /**
     * Logger for TrainService class.
     */
    private static Logger log = Logger.getLogger(TrainService.class.getName());

    private TrainDao trainDao;
    private TicketDao ticketDao;
    private StationDao stationDao;
    private ScheduleDao scheduleDao;

    public TrainService(DaoFactory daoFactory) {
        trainDao = daoFactory.getTrainDao();
        ticketDao = daoFactory.getTicketDao();
        stationDao = daoFactory.getStationDao();
        scheduleDao = daoFactory.getScheduleDao();
    }

    public boolean hasEmptySeats(int trainId) {
        Train train = trainDao.read(trainId);
        if (train != null) {
            int seats = train.getSeats();
            int count = ticketDao.count(trainId);
            if (count < seats) {
                return true;
            }
        }
        return false;
    }

    public Integer getTrainId(int trainNumber) {
        Integer trainId = null;
        Train train = trainDao.findTrain(trainNumber);
        if (train != null) {
            trainId = train.getId();
        }
        return trainId;
    }

    public boolean isRegistered(int trainId, int userId) {
        return ticketDao.isRegistered(trainId, userId);
    }

    public Date getDepartureDate(String stationFromName, int trainId) {
        Date timeDeparture = null;
        Station station = stationDao.findStation(stationFromName);
        if (station != null) {
            int stationId = station.getId();
            List<Schedule> schedules = scheduleDao.getSchedules(stationId, trainId);
            if (schedules.size() == 1) {
                timeDeparture = schedules.get(0).getTimeDeparture();
            }
        }
        return timeDeparture;
    }

    public void buyTicket(int userId, int trainId) {
        log.info("userId: " + userId + " trainId: " + trainId);
    }
}
