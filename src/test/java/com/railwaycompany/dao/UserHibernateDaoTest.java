package com.railwaycompany.dao;

import com.railwaycompany.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserHibernateDaoTest {

    private DaoFactory daoFactory;
    private UserHibernateDao userHibernateDao;

    @Before
    public void setUp() throws Exception {
        daoFactory = new HibernateDaoFactory("RailwayInformationSystemTest");
        userHibernateDao = daoFactory.getUserHibernateDao();
    }

    @Test
    public void testFindUser() throws Exception {
        User user = userHibernateDao.findUser("testLogin", "testPassword");
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getLogin(), "testLogin");
        Assert.assertEquals(user.getPassword(), "testPassword");
        user = userHibernateDao.findUser("notExistLogin", "notExistPassword");
        Assert.assertNull(user);
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.close();
    }
}