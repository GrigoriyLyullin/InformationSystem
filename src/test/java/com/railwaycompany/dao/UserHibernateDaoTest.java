package com.railwaycompany.dao;

import com.railwaycompany.entities.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UserHibernateDaoTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserHibernateDao userHibernateDao;

    @Before
    public void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory("RailwayInformationSystemTest");
        entityManager = entityManagerFactory.createEntityManager();
        userHibernateDao = new UserHibernateDao(entityManager);
    }

    @Test
    public void testFindUser() throws Exception {

        User user = userHibernateDao.findUser("testLogin", "testPassword");

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getLogin(), "testLogin");
        Assert.assertEquals(user.getPassword(), "testPassword");

    }

    @After
    public void tearDown() throws Exception {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}