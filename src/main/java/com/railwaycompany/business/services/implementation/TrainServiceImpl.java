package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.interfaces.TrainService;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    @Autowired
    private TrainDao trainDao;

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public List<TrainData> getAll() {
        List<TrainData> trainDataList = null;
        List<Train> trainList = trainDao.getAll();
        if (trainList != null && !trainList.isEmpty()) {
            trainDataList = new ArrayList<>();
            for (Train t : trainList) {
                TrainData trainData = new TrainData();
                trainData.setId(t.getId());
                trainData.setNumber(t.getNumber());
                trainData.setSeats(t.getSeats());

                List<StationData> stationDataList = null;
                List<Schedule> schedules = scheduleDao.getSchedulesByTrainId(t.getId());
                if (schedules != null && !schedules.isEmpty()) {
                    stationDataList = new ArrayList<>();
                    for (Schedule schedule : schedules) {
                        Station station = schedule.getStation();
                        StationData stationData = new StationData();
                        stationData.setId(station.getId());
                        stationData.setName(station.getName());
                        stationData.setArrivalDate(schedule.getTimeArrival());
                        stationData.setDepartureDate(schedule.getTimeDeparture());
                        stationDataList.add(stationData);
                    }
                }
                trainData.setStations(stationDataList);
                trainDataList.add(trainData);
            }
        }
        return trainDataList;
    }

    @Override
    public void addTrain(int trainNumber, int trainSeats, boolean addAnyway) throws TrainWithSuchNumberExistException {
        List<Train> trainList = trainDao.findTrains(trainNumber, trainSeats);
        if (trainList == null || trainList.isEmpty() || addAnyway) {
            Train train = new Train();
            train.setNumber(trainNumber);
            train.setSeats(trainSeats);
            trainDao.create(train);
        } else {
            String msg = "Train with number: " + trainNumber + " and seats " + trainSeats + " already exist. Add " +
                    "anyway mode: false";
            throw new TrainWithSuchNumberExistException(msg);
        }
    }
}
