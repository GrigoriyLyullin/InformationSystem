package com.railwaycompany.dao;

import org.junit.Assert;
import org.junit.Test;

public class HibernateDaoFactorySingletonTest {

    @Test
    public void testGetInstance() throws Exception {
        DaoFactory instance1 = HibernateDaoFactorySingleton.getInstance();
        DaoFactory instance2 = HibernateDaoFactorySingleton.getInstance();
        Assert.assertNotNull(instance1);
        Assert.assertNotNull(instance1.getScheduleDao());
        Assert.assertNotNull(instance1.getStationDao());
        Assert.assertNotNull(instance1.getUserDao());
        Assert.assertEquals(instance1, instance2);
        Assert.assertEquals(instance1.getScheduleDao(), instance2.getScheduleDao());
        Assert.assertEquals(instance1.getStationDao(), instance2.getStationDao());
        Assert.assertEquals(instance1.getUserDao(), instance2.getUserDao());
    }
}