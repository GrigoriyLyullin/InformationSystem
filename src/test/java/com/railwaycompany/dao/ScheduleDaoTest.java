package com.railwaycompany.dao;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.ScheduleDao;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.dao.hibernateDao.HibernateDaoFactory;
import com.railwaycompany.utils.DateHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ScheduleDaoTest {

    private static final int STATION_FROM_ID = 1;

    private DaoFactory daoFactory;
    private ScheduleDao scheduleDao;

    @Before
    public void setUp() throws Exception {
        daoFactory = new HibernateDaoFactory("RailwayInformationSystemTest");
        scheduleDao = daoFactory.getScheduleDao();
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.close();
    }

    @Test
    public void testGetSchedulesByStationId() throws Exception {
        List<Schedule> schedules = scheduleDao.getSchedules(STATION_FROM_ID);
        Assert.assertNotNull(schedules);
        Assert.assertTrue(!schedules.isEmpty());
        for (Schedule s : schedules) {
            Assert.assertNotNull(s.getTrain());
            Station currStation = s.getStation();
            Assert.assertNotNull(currStation);
            Assert.assertEquals(currStation.getId(), STATION_FROM_ID);
        }
        int notExistStationId = Integer.MAX_VALUE;
        schedules = scheduleDao.getSchedules(notExistStationId);
        Assert.assertTrue(schedules.isEmpty());
    }

    @Test
    public void testGetSchedulesByStationIdAndDepartureDate() throws Exception {
        Date departureDate = DateHelper.convertDate("2015-02-02");
        List<Schedule> schedules = scheduleDao.getSchedules(STATION_FROM_ID, departureDate);
        Assert.assertNotNull(schedules);
        Assert.assertTrue(!schedules.isEmpty());
        Assert.assertTrue(schedules.size() >= 2);

        departureDate = DateHelper.convertDatetime("2015-02-02 12:00");
        schedules = scheduleDao.getSchedules(STATION_FROM_ID, departureDate);
        Assert.assertTrue(schedules.size() >= 1);
    }

    @Test
    public void testGetSchedulesByStationIdAndArrivalDate() throws Exception {
        Date arrivalDate = DateHelper.convertDate("2015-02-02");
        List<Schedule> schedules = scheduleDao.getSchedules(arrivalDate, STATION_FROM_ID);
        Assert.assertNotNull(schedules);
        Assert.assertTrue(schedules.isEmpty());

        arrivalDate = DateHelper.convertDatetime("2015-02-02 11:00");
        schedules = scheduleDao.getSchedules(STATION_FROM_ID, arrivalDate);
        Assert.assertTrue(schedules.size() >= 1);
    }
}