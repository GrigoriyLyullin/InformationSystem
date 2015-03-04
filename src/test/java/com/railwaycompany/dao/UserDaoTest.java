package com.railwaycompany.dao;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.UserDao;
import com.railwaycompany.entities.User;
import com.railwaycompany.dao.hibernateDao.HibernateDaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {

    private DaoFactory daoFactory;
    private UserDao userDao;

    @Before
    public void setUp() throws Exception {
        daoFactory = new HibernateDaoFactory("RailwayInformationSystemTest");
        userDao = daoFactory.getUserDao();
    }

    @Test
    public void testFindUser() throws Exception {
        User user = userDao.findUser("testLogin", "testPassword");
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getLogin(), "testLogin");
        Assert.assertEquals(user.getPassword(), "testPassword");
        user = userDao.findUser("notExistLogin", "notExistPassword");
        Assert.assertNull(user);
    }

    @After
    public void tearDown() throws Exception {
        daoFactory.close();
    }
}