package com.railwaycompany.dao;

import com.railwaycompany.entities.Train;
import org.junit.Assert;
import org.junit.Before;

public class HibernateDaoTestTrain extends HibernateDaoTest<Train> {

    private Train testTrain;

    @Before
    public void setUp() throws Exception {
        entityClass = Train.class;
        dao = new HibernateDao<>(EntityManagerSingleton.getInstance(persistenceUnitName), entityClass);

        testTrain = new Train();
        testTrain.setNumber(456);
        testTrain.setSeats(123);
    }

    @Override
    public void testCreate() throws Exception {
        dao.create(testTrain);
        Train readTrain = dao.read(testTrain.getId());
        Assert.assertNotNull(readTrain);
        Assert.assertEquals(readTrain.getNumber(), testTrain.getNumber());
        Assert.assertEquals(readTrain.getSeats(), testTrain.getSeats());
        dao.delete(testTrain);
    }

    @Override
    public void testRead() throws Exception {
        Assert.assertNull(dao.read(testTrain.getId()));
        dao.create(testTrain);
        Train readTrain = dao.read(testTrain.getId());
        Assert.assertNotNull(readTrain);
        Assert.assertEquals(readTrain.getNumber(), testTrain.getNumber());
        Assert.assertEquals(readTrain.getSeats(), testTrain.getSeats());
        dao.delete(testTrain);
    }

    @Override
    public void testUpdate() throws Exception {
        dao.create(testTrain);

        int number = testTrain.getNumber();
        int seats = testTrain.getSeats();
        testTrain.setNumber(testTrain.getNumber() + 1);
        testTrain.setSeats(testTrain.getSeats() + 1);

        dao.update(testTrain);

        Train updatedTrain = dao.read(testTrain.getId());
        Assert.assertNotNull(updatedTrain);
        Assert.assertNotEquals(number, updatedTrain.getNumber());
        Assert.assertNotEquals(seats, updatedTrain.getSeats());
        Assert.assertEquals(updatedTrain, testTrain);

        dao.delete(testTrain);
    }

    @Override
    public void testDelete() throws Exception {
        dao.create(testTrain);
        Assert.assertNotNull(dao.read(testTrain.getId()));
        dao.delete(testTrain);
        Assert.assertNull(dao.read(testTrain.getId()));
    }
}
