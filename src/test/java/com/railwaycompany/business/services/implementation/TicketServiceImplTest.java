package com.railwaycompany.business.services.implementation;

import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.dao.interfaces.TrainDao;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.business.services.interfaces.TicketService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceImplTest {

    private User user;
    private Train train;

    private TicketService ticketService;

    @Before
    public void setUp() throws Exception {

        user = new User();
        user.setId(1);

        train = new Train();
        train.setId(1);

        TicketDao ticketDao = mock(TicketDao.class);
        TrainDao trainDao = mock(TrainDao.class);
        UserDao userDao = mock(UserDao.class);

        when(userDao.read(1)).thenReturn(user);
        when(trainDao.read(1)).thenReturn(train);

        ticketService = new TicketServiceImpl(ticketDao, trainDao, userDao);
    }

    @Test
    public void testBuyTicket() throws Exception {
        Assert.assertTrue(ticketService.buyTicket(user.getId(), train.getId()));
    }
}