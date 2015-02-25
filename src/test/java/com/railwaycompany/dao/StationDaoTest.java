package com.railwaycompany.dao;

import com.railwaycompany.entities.Station;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StationDaoTest {

    private DaoFactory daoFactory;
    private StationDao stationDao;

    @Before
    public void setUp() throws Exception {
        daoFactory = new HibernateDaoFactory("RailwayInformationSystemTest");
        stationDao = daoFactory.getStationDao();
    }

    @Test
    public void testFindStation() throws Exception {
        String stationName = "testStationName";
        Station station = stationDao.findStation(stationName);
        Assert.assertNotNull(station);
        Assert.assertEquals(station.getName(), stationName);
        station = stationDao.findStation("NotExistStationName");
        Assert.assertNull(station);
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.close();
    }
}