package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class StationServiceImplTest {

    private static final int STATION_ID = 1;
    private static final String STATION_NAME = "testStationName";
    private static final String NOT_EXIST_STATION_NAME = "testNotExistStationName";

    @Autowired
    private StationService stationService;

    @Test
    public void testGetStation() throws Exception {
        StationData stationData = stationService.getStation(STATION_NAME);
        Assert.assertNotNull(stationData);
        Assert.assertEquals(stationData.getId(), STATION_ID);
        Assert.assertEquals(stationData.getName(), STATION_NAME);
    }

    @Test(expected = StationWithSuchNameExistException.class)
    public void testAddExistStation() throws Exception {
        stationService.addStation(STATION_NAME);
    }

    @Test
    public void testAddStation() throws Exception {
        stationService.addStation(NOT_EXIST_STATION_NAME);
    }

    @Test
    public void testGetAll() throws Exception {
        List<StationData> stationDataList = stationService.getAll();
        Assert.assertNotNull(stationDataList);
        Assert.assertFalse(stationDataList.isEmpty());
        StationData stationData = stationDataList.get(0);
        Assert.assertEquals(stationData.getId(), STATION_ID);
        Assert.assertEquals(stationData.getName(), STATION_NAME);
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public StationService stationService() {
            return new StationServiceImpl();
        }

        @Bean
        public StationDao stationDao() {
            Station station = new Station();
            station.setId(STATION_ID);
            station.setName(STATION_NAME);

            List<Station> stationList = new ArrayList<>();
            stationList.add(station);

            StationDao stationDao = mock(StationDao.class);

            when(stationDao.getStation(STATION_NAME)).thenReturn(station);
            when(stationDao.getStation(NOT_EXIST_STATION_NAME)).thenReturn(null);
            when(stationDao.getAll()).thenReturn(stationList);

            return stationDao;
        }
    }
}