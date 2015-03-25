package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.entities.Schedule;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.utils.DateHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScheduleServiceImplTest {

    private static final int STATION_FROM_ID = 1;
    private static final int STATION_TO_ID = 2;
    private static final int STATION_NOT_EXIST_ID = 3;
    private static final int TRAIN_ID = 1;
    private static final int TRAIN_NUMBER = 123;

    private Station stationFrom;
    private Station stationTo;
    private Station notExistStation;

    private Date dateArrivalStationFrom = DateHelper.convertDatetime("2015-02-20 11:00");
    private Date dateDepartureStationFrom = DateHelper.convertDatetime("2015-02-20 11:30");
    private Date dateArrivalStationTo = DateHelper.convertDatetime("2015-02-21 21:05");
    private Date dateDepartureStationTo = DateHelper.convertDatetime("2015-02-21 21:43");
    private Date notExistentDate = new Date();

    private ScheduleService scheduleService;

    @Before
    public void setUp() throws Exception {

        stationFrom = new Station();
        stationFrom.setId(STATION_FROM_ID);
        stationFrom.setName("testStationFromName");

        stationTo = new Station();
        stationTo.setId(STATION_TO_ID);
        stationTo.setName("testStationToName");

        notExistStation = new Station();
        notExistStation.setId(STATION_NOT_EXIST_ID);
        notExistStation.setName("notExistStationName");

        Train train = new Train();
        train.setId(TRAIN_ID);
        train.setNumber(TRAIN_NUMBER);

        Schedule scheduleStationFrom = new Schedule();
        scheduleStationFrom.setId(1);
        scheduleStationFrom.setStation(stationFrom);
        scheduleStationFrom.setTimeArrival(dateArrivalStationFrom);
        scheduleStationFrom.setTimeDeparture(dateDepartureStationFrom);
        scheduleStationFrom.setTrain(train);

        Schedule scheduleStationTo = new Schedule();
        scheduleStationTo.setId(2);
        scheduleStationTo.setStation(stationTo);
        scheduleStationTo.setTimeArrival(dateArrivalStationTo);
        scheduleStationTo.setTimeDeparture(dateDepartureStationTo);
        scheduleStationTo.setTrain(train);

        List<Schedule> scheduleStationFromList = new ArrayList<>();
        scheduleStationFromList.add(scheduleStationFrom);
        List<Schedule> scheduleStationToList = new ArrayList<>();
        scheduleStationToList.add(scheduleStationTo);

        ScheduleDao scheduleDaoMock = mock(ScheduleDao.class);

        when(scheduleDaoMock.getSchedulesByStationId(STATION_FROM_ID)).thenReturn(scheduleStationFromList);
        when(scheduleDaoMock.getSchedulesByStationId(STATION_TO_ID)).thenReturn(scheduleStationToList);
        when(scheduleDaoMock.getSchedulesByStationId(STATION_NOT_EXIST_ID)).thenReturn(null);
        when(scheduleDaoMock.getSchedules(STATION_FROM_ID, dateDepartureStationFrom)).thenReturn(scheduleStationFromList);
        when(scheduleDaoMock.getSchedules(STATION_NOT_EXIST_ID, dateDepartureStationFrom)).thenReturn(null);
        when(scheduleDaoMock.getSchedules(STATION_TO_ID, TRAIN_ID)).thenReturn(scheduleStationToList);
        when(scheduleDaoMock.getSchedules(STATION_FROM_ID, dateDepartureStationFrom)).thenReturn(scheduleStationFromList);
        when(scheduleDaoMock.getSchedules(STATION_TO_ID, dateDepartureStationFrom)).thenReturn(scheduleStationToList);
        when(scheduleDaoMock.getSchedules(dateArrivalStationTo, STATION_TO_ID)).thenReturn(scheduleStationToList);

        scheduleService = new ScheduleServiceImpl();
    }


    @Test
    public void testGetScheduleByStation() throws Exception {
        List<ScheduleData> schedule = scheduleService.getSchedule(stationFrom);
        assertNotNull(schedule);
        assertFalse(schedule.isEmpty());

        ScheduleData scheduleData = schedule.get(0);
        assertEquals(scheduleData.getTrainId(), TRAIN_ID);
        assertEquals(scheduleData.getTrainNumber(), TRAIN_NUMBER);
        assertEquals(scheduleData.getTimeArrival(), dateArrivalStationFrom);
        assertEquals(scheduleData.getTimeDeparture(), dateDepartureStationFrom);

        schedule = scheduleService.getSchedule(notExistStation);
        assertNull(schedule);
    }

    @Test
    public void testGetScheduleByStationsAndDepartureDate() throws Exception {
        List<ScheduleData> schedule = scheduleService.getSchedule(stationFrom, stationTo, dateDepartureStationFrom);
        assertNotNull(schedule);
        assertFalse(schedule.isEmpty());

        ScheduleData scheduleData = schedule.get(0);
        assertEquals(scheduleData.getTrainId(), TRAIN_ID);
        assertEquals(scheduleData.getTrainNumber(), TRAIN_NUMBER);
        assertEquals(scheduleData.getTimeDeparture(), dateArrivalStationTo);
        assertEquals(scheduleData.getTimeArrival(), dateDepartureStationFrom);

        schedule = scheduleService.getSchedule(notExistStation, stationTo, dateDepartureStationFrom);
        assertNull(schedule);
        schedule = scheduleService.getSchedule(stationFrom, notExistStation, dateDepartureStationFrom);
        assertNull(schedule);
        schedule = scheduleService.getSchedule(stationFrom, stationTo, notExistentDate);
        assertNull(schedule);
    }

    @Test
    public void testGetScheduleByStationsAndDates() throws Exception {
        List<ScheduleData> schedule = scheduleService.getSchedule(stationFrom, stationTo, dateDepartureStationFrom,
                dateArrivalStationTo);
        assertNotNull(schedule);
        assertFalse(schedule.isEmpty());

        ScheduleData scheduleData = schedule.get(0);
        assertEquals(scheduleData.getTrainId(), TRAIN_ID);
        assertEquals(scheduleData.getTrainNumber(), TRAIN_NUMBER);
        assertEquals(scheduleData.getTimeDeparture(), dateArrivalStationTo);
        assertTrue(scheduleData.getTimeArrival().getTime() <= dateArrivalStationTo.getTime());

        schedule = scheduleService.getSchedule(notExistStation, stationTo, dateDepartureStationFrom,
                dateArrivalStationTo);
        assertNull(schedule);
        schedule = scheduleService.getSchedule(stationFrom, notExistStation, dateDepartureStationFrom,
                dateArrivalStationTo);
        assertNull(schedule);
        schedule = scheduleService.getSchedule(stationFrom, stationTo, notExistentDate,
                dateArrivalStationTo);
        assertNull(schedule);
        schedule = scheduleService.getSchedule(stationFrom, stationTo, dateDepartureStationFrom,
                notExistentDate);
        assertNull(schedule);
    }
}