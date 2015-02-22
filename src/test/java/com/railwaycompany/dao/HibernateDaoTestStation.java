package com.railwaycompany.dao;

import com.railwaycompany.entities.Station;
import org.junit.Assert;
import org.junit.Before;

public class HibernateDaoTestStation extends HibernateDaoTest<Station> {

    private Station testStation;

    @Before
    public void setUp() throws Exception {

        entityClass = Station.class;
        dao = new HibernateDao<>(EntityManagerSingleton.getInstance(persistenceUnitName), entityClass);

        testStation = new Station();
        testStation.setName("testStation");
    }

    @Override
    public void testCreate() throws Exception {
        dao.create(testStation);
        Assert.assertNotNull(dao.read(testStation.getId()));
        dao.delete(testStation);
    }

    @Override
    public void testRead() throws Exception {
        dao.create(testStation);
        Station readStation = dao.read(testStation.getId());
        Assert.assertNotNull(readStation);
        Assert.assertEquals(readStation.getName(), testStation.getName());
        dao.delete(testStation);
    }

    @Override
    public void testUpdate() throws Exception {
        dao.create(testStation);
        String name = testStation.getName();
        testStation.setName("updated " + name);
        Station updatedStation = dao.read(testStation.getId());
        Assert.assertNotEquals(name, updatedStation.getName());
        dao.delete(testStation);
    }

    @Override
    public void testDelete() throws Exception {
        dao.create(testStation);
        Assert.assertNotNull(dao.read(testStation.getId()));
        dao.delete(testStation);
        Assert.assertNull(dao.read(testStation.getId()));
    }
}
