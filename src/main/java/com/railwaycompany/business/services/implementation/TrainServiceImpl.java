package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.interfaces.TrainService;
import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Train;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TrainServiceImpl implements TrainService {

    /**
     * Logger for TrainServiceImpl class.
     */
    private static Logger log = Logger.getLogger(TrainServiceImpl.class.getName());

    private TrainDao trainDao;

    public TrainServiceImpl(DaoContext daoContext) {
        trainDao = (TrainDao) daoContext.get(TrainDao.class);
    }

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
