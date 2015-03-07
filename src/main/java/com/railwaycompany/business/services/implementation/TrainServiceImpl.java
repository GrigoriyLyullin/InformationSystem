package com.railwaycompany.business.services.implementation;

import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.business.services.interfaces.TrainService;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class TrainServiceImpl implements TrainService {

    /**
     * Logger for TrainServiceImpl class.
     */
    private static Logger log = Logger.getLogger(TrainServiceImpl.class.getName());

    private TrainDao trainDao;
    private TicketDao ticketDao;
    private StationDao stationDao;
    private ScheduleDao scheduleDao;

    public TrainServiceImpl(TrainDao trainDao, TicketDao ticketDao, StationDao stationDao, ScheduleDao scheduleDao) {
        this.trainDao = trainDao;
        this.ticketDao = ticketDao;
        this.stationDao = stationDao;
        this.scheduleDao = scheduleDao;
    }

    @Override
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

    @Override
    public Integer getTrainId(int trainNumber) {
        Integer trainId = null;
        Train train = trainDao.findTrain(trainNumber);
        if (train != null) {
            trainId = train.getId();
        }
        return trainId;
    }

    @Override
    public boolean isRegistered(int trainId, int userId) {
        return ticketDao.isRegistered(trainId, userId);
    }

    @Override
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
}
