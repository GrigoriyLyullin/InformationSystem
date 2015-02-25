package com.railwaycompany.dao;

import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ScheduleDaoTest {

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
        int stationId = 1;
        List<Schedule> schedulesByStationId = scheduleDao.getSchedulesByStationId(stationId);
        Assert.assertNotNull(schedulesByStationId);
        Assert.assertTrue(!schedulesByStationId.isEmpty());
        for (Schedule s : schedulesByStationId) {
            Assert.assertNotNull(s.getTrain());
            Station currStation = s.getStation();
            Assert.assertNotNull(currStation);
            Assert.assertEquals(currStation.getId(), stationId);
        }
        int notExistStationId = Integer.MAX_VALUE;
        schedulesByStationId = scheduleDao.getSchedulesByStationId(notExistStationId);
        Assert.assertTrue(schedulesByStationId.isEmpty());
    }
}