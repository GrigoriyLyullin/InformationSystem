package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.interfaces.PassengerService;
import com.railwaycompany.persistence.dao.hibernate.HibernateDaoContext;
import com.railwaycompany.persistence.dao.interfaces.DaoContext;
import com.railwaycompany.persistence.dao.interfaces.TicketDao;
import com.railwaycompany.persistence.entities.Passenger;
import com.railwaycompany.persistence.entities.Ticket;
import com.railwaycompany.persistence.entities.Train;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PassengerServiceImplTest {

    private static final int TRAIN_ID = 1;
    private static final int NOT_EXIST_TRAIN_ID = 0;
    private static final int EMPTY_TRAIN_ID = 2;
    private static final int PASSENGER_ID = 2;
    private static final int TICKET_ID = 3;
    private static final String PASSENGER_NAME = "passengerName";
    private static final String PASSENGER_SURNAME = "passengerSurname";
    private static final Date PASSENGER_BIRTHDATE = new Date();

    private PassengerService passengerService;

    @Before
    public void setUp() throws Exception {

        Train train = new Train();
        train.setId(TRAIN_ID);

        Passenger passenger = new Passenger();
        passenger.setId(PASSENGER_ID);
        passenger.setName(PASSENGER_NAME);
        passenger.setSurname(PASSENGER_SURNAME);
        passenger.setBirthdate(PASSENGER_BIRTHDATE);

        Ticket ticket = new Ticket();
        ticket.setId(TICKET_ID);
        ticket.setTrain(train);
        ticket.setPassenger(passenger);

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);

        TicketDao ticketDao = mock(TicketDao.class);

        when(ticketDao.getTicketsByTrainId(TRAIN_ID)).thenReturn(ticketList);
        when(ticketDao.getTicketsByTrainId(NOT_EXIST_TRAIN_ID)).thenReturn(null);
        when(ticketDao.getTicketsByTrainId(EMPTY_TRAIN_ID)).thenReturn(new ArrayList<Ticket>());

        DaoContext daoContext = new HibernateDaoContext();
        daoContext.put(TicketDao.class, ticketDao);
        passengerService = new PassengerServiceImpl(daoContext);
    }

    @Test
    public void testGetAllPassengersByTrainId() throws Exception {
        List<PassengerData> allPassengers = passengerService.getAllPassengersByTrainId(TRAIN_ID);
        Assert.assertNotNull(allPassengers);
        Assert.assertFalse(allPassengers.isEmpty());
        PassengerData passengerData = allPassengers.get(0);
        Assert.assertEquals(passengerData.getId(), PASSENGER_ID);
        Assert.assertEquals(passengerData.getName(), PASSENGER_NAME);
        Assert.assertEquals(passengerData.getSurname(), PASSENGER_SURNAME);
        Assert.assertEquals(passengerData.getBirthdate(), PASSENGER_BIRTHDATE);
    }

    @Test
    public void testGetAllPassengersByNotExistTrainId() throws Exception {
        List<PassengerData> allPassengers = passengerService.getAllPassengersByTrainId(NOT_EXIST_TRAIN_ID);
        Assert.assertNull(allPassengers);
    }

    @Test
    public void testGetAllPassengersByEmptyTrainId() throws Exception {
        List<PassengerData> allPassengers = passengerService.getAllPassengersByTrainId(EMPTY_TRAIN_ID);
        Assert.assertNull(allPassengers);
    }
}