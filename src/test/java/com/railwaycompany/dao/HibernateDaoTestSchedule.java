package com.railwaycompany.dao;

import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;
import org.junit.Assert;
import org.junit.Before;

import java.util.Date;

public class HibernateDaoTestSchedule extends HibernateDaoTest<Schedule> {

    private Schedule testSchedule;
    private Train testTrain;
    private Station testStation;

    @Before
    public void setUp() throws Exception {
        entityClass = Schedule.class;
        dao = new HibernateDao<>(EntityManagerSingleton.getInstance(persistenceUnitName), entityClass);

        testTrain = new Train();
        testTrain.setId(1);
        testTrain.setNumber(123);
        testTrain.setSeats(456);

        testStation = new Station();
        testStation.setId(1);
        testStation.setName("testStation");

        testSchedule = new Schedule();
        testSchedule.setTrain(testTrain);
        testSchedule.setStation(testStation);
        testSchedule.setTimeArrival(new Date());
        testSchedule.setTimeDeparture(new Date());
    }

    @Override
    public void testCreate() throws Exception {
        dao.create(testSchedule);
        Schedule readSchedule = dao.read(testSchedule.getId());
        Assert.assertNotNull(readSchedule);
        Assert.assertNotNull(readSchedule.getTrain());
        Assert.assertNotNull(readSchedule.getStation());
        dao.delete(testSchedule);
    }

    @Override
    public void testRead() throws Exception {
        dao.create(testSchedule);
        Schedule readSchedule = dao.read(testSchedule.getId());
        Assert.assertEquals(readSchedule, testSchedule);
        Assert.assertEquals(readSchedule.getTrain(), testTrain);
        Assert.assertEquals(readSchedule.getStation(), testStation);
        dao.delete(testSchedule);
    }

    @Override
    public void testUpdate() throws Exception {
        dao.create(testSchedule);

        Date timeArrival = testSchedule.getTimeArrival();
        Date timeDeparture = testSchedule.getTimeDeparture();

        testSchedule.setTimeArrival(new Date(timeArrival.getTime() - millisInDay));
        testSchedule.setTimeDeparture(new Date(timeDeparture.getTime() - millisInDay));
        testSchedule.setTrain(new Train());
        testSchedule.setStation(new Station());

        Schedule updatedSchedule = dao.read(testSchedule.getId());
        Assert.assertNotEquals(updatedSchedule.getTimeArrival(), timeArrival);
        Assert.assertNotEquals(updatedSchedule.getTimeDeparture(), timeDeparture);
        Assert.assertNotEquals(updatedSchedule.getTrain(), testTrain);
        Assert.assertNotEquals(updatedSchedule.getStation(), testStation);

        dao.delete(testSchedule);
    }

    @Override
    public void testDelete() throws Exception {
        dao.create(testSchedule);
        Schedule readSchedule = dao.read(testSchedule.getId());
        Assert.assertNotNull(readSchedule);
        Assert.assertNotNull(readSchedule.getTrain());
        Assert.assertNotNull(readSchedule.getStation());
        dao.delete(testSchedule);
        Schedule deletedSchedule = dao.read(testSchedule.getId());
        Assert.assertNull(deletedSchedule);
    }
}
