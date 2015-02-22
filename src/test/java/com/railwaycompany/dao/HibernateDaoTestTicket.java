package com.railwaycompany.dao;

import com.railwaycompany.entities.Ticket;
import com.railwaycompany.entities.Train;
import com.railwaycompany.entities.User;
import org.junit.Assert;
import org.junit.Before;

import java.util.Date;

public class HibernateDaoTestTicket extends HibernateDaoTest<Ticket> {

    private Ticket testTicket;
    private Train testTrain;
    private User testUser;

    @Before
    public void setUp() throws Exception {
        entityClass = Ticket.class;
        dao = new HibernateDao<>(EntityManagerSingleton.getInstance(persistenceUnitName), entityClass);

        testTrain = new Train();
        testTrain.setId(1);
        testTrain.setNumber(456);
        testTrain.setSeats(123);

        testUser = new User();
        testUser.setId(1);
        testUser.setLogin("testLogin");
        testUser.setPassword("testPassword");
        testUser.setName("testName");
        testUser.setSurname("testSurname");
        testUser.setEmployee(false);
        testUser.setBirthdate(new Date());

        testTicket = new Ticket();
        testTicket.setTrain(testTrain);
        testTicket.setUser(testUser);
    }

    @Override
    public void testCreate() throws Exception {
        dao.create(testTicket);
        Ticket createdTicket = dao.read(testTicket.getId());
        Assert.assertNotNull(createdTicket);
        Assert.assertNotNull(createdTicket.getTrain());
        Assert.assertNotNull(createdTicket.getUser());
        dao.delete(testTicket);
    }

    @Override
    public void testRead() throws Exception {
        dao.create(testTicket);
        Ticket readTicket = dao.read(testTicket.getId());
        Train train = readTicket.getTrain();
        User user = readTicket.getUser();
        Assert.assertEquals(train, testTrain);
        Assert.assertEquals(user, testUser);
        dao.delete(testTicket);
    }

    @Override
    public void testUpdate() throws Exception {
        dao.create(testTicket);

        Train newTrain = new Train();
        newTrain.setId(2);
        User newUser = new User();
        newUser.setId(2);

        testTicket.setTrain(newTrain);
        testTicket.setUser(newUser);

        dao.update(testTicket);
        Assert.assertNotEquals(testTicket.getTrain(), testTrain);
        Assert.assertNotEquals(testTicket.getUser(), testUser);
        dao.delete(testTicket);
    }

    @Override
    public void testDelete() throws Exception {
        dao.create(testTicket);
        Ticket createdTicket = dao.read(testTicket.getId());
        Assert.assertNotNull(createdTicket);
        dao.delete(testTicket);
        Ticket deletedTicket = dao.read(testTicket.getId());
        Assert.assertNull(deletedTicket);
    }
}
