package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.interfaces.TrainService;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.utils.DateHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrainServiceImplTest {

    private static final int TRAIN_ID = 1;
    private static final int TRAIN_NUMBER = 123;
    private static final int NOT_EXIST_TRAIN_NUMBER = 666;
    private static final int TRAIN_SEATS = 321;

    private static final int STATION_ID = 2;
    private static final String STATION_NAME = "testStationName";

    private static final int SCHEDULE_ID = 3;
    private static final Date SCHEDULE_TIME_ARRIVAL = new Date();
    private static final Date SCHEDULE_TIME_DEPARTURE = new Date(SCHEDULE_TIME_ARRIVAL.getTime() + DateHelper
            .MILLIS_IN_10_MINUTES);

    private TrainService trainService;

    @Before
    @Ignore
    public void setUp() throws Exception {

        Train train = new Train();
        train.setId(TRAIN_ID);
        train.setNumber(TRAIN_NUMBER);
        train.setSeats(TRAIN_SEATS);

        List<Train> trainList = new ArrayList<>();
        trainList.add(train);

        Station station = new Station();
        station.setId(STATION_ID);
        station.setName(STATION_NAME);

        Schedule schedule = new Schedule();
        schedule.setId(SCHEDULE_ID);
        schedule.setTimeArrival(SCHEDULE_TIME_ARRIVAL);
        schedule.setTimeDeparture(SCHEDULE_TIME_DEPARTURE);
        schedule.setTrain(train);
        schedule.setStation(station);

        List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(schedule);

        TrainDao trainDao = mock(TrainDao.class);
        ScheduleDao scheduleDao = mock(ScheduleDao.class);

        when(trainDao.findTrains(TRAIN_NUMBER, TRAIN_SEATS)).thenReturn(trainList);
        when(trainDao.getAll()).thenReturn(trainList);
        when(scheduleDao.getSchedulesByTrainId(TRAIN_ID)).thenReturn(scheduleList);

        trainService = new TrainServiceImpl();
    }

    @Test
    @Ignore
    public void testGetAll() throws Exception {
        List<TrainData> trainDataList = trainService.getAll();
        Assert.assertNotNull(trainDataList);
        Assert.assertFalse(trainDataList.isEmpty());
    }

    @Test(expected = TrainWithSuchNumberExistException.class)
    @Ignore
    public void testAddExistTrain() throws Exception {
        trainService.addTrain(TRAIN_NUMBER, TRAIN_SEATS, false);
    }

    @Test
    @Ignore
    public void testAddExistTrainAnyway() throws Exception {
        trainService.addTrain(NOT_EXIST_TRAIN_NUMBER, TRAIN_SEATS, false);
    }
}