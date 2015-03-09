package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.persistence.dao.hibernate.HibernateDaoContext;
import com.railwaycompany.persistence.dao.interfaces.*;
import com.railwaycompany.persistence.entities.Passenger;
import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.persistence.entities.Train;
import com.railwaycompany.utils.DateHelper;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TicketServiceImplTest {

    private static final int USER_ID = 1;

    private static final int TRAIN_ID = 1;
    private static final int TRAIN_NUMBER = 123;
    private static final int TRAIN_SEATS = 2;

    private static final int FULL_TRAIN_ID = 2;
    private static final int FULL_TRAIN_NUMBER = 124;
    private static final int FULL_TRAIN_SEATS = 2;

    private static final int STOP_SALE_TRAIN_ID = 3;
    private static final int STOP_SALE_TRAIN_NUMBER = 125;
    private static final int STOP_SALE_TRAIN_SEATS = 2;

    private static final int STATION_ID = 1;
    private static final String STATION_FROM_NAME = "testStationName";

    private static final Date SCHEDULE_DEPARTURE_DATE = new Date(new Date().getTime() + DateHelper
            .MILLIS_IN_10_MINUTES + DateHelper.MILLIS_IN_1_MINUTE);

    private static final String REGISTERED_PASSENGER_SURNAME = "passengerSurname";
    private static final Date REGISTERED_PASSENGER_BIRTHDATE = DateHelper.convertDate("1990-02-02");

    private static final int REGISTERED_PASSENGER_ID = 1;
    private static final String REGISTERED_PASSENGER_NAME = "registeredPassengerName";
    private static final String NOT_EXIST_STATION = "notExistStationName";
    private static final int NOT_EXIST_TRAIN_NUMBER = 666;

    private static final Date IN_TIME_DEPARTURE_DATE_TIME = new Date(SCHEDULE_DEPARTURE_DATE.getTime() - DateHelper
            .MILLIS_IN_10_MINUTES);

    private static final Date SALE_STOP_DEPARTURE_DATE_TIME = new Date();


    private PassengerData passengerData;
    private TicketService ticketService;

    @Before
    public void setUp() throws Exception {

        Train train = new Train();
        train.setId(TRAIN_ID);
        train.setNumber(TRAIN_NUMBER);
        train.setSeats(TRAIN_SEATS);

        Train fullTrain = new Train();
        fullTrain.setId(FULL_TRAIN_ID);
        fullTrain.setNumber(FULL_TRAIN_NUMBER);
        fullTrain.setSeats(FULL_TRAIN_SEATS);

        Train stopSaleTrain = new Train();
        stopSaleTrain.setId(STOP_SALE_TRAIN_ID);
        stopSaleTrain.setNumber(STOP_SALE_TRAIN_NUMBER);
        stopSaleTrain.setSeats(STOP_SALE_TRAIN_SEATS);

        Station station = new Station();
        station.setId(STATION_ID);
        station.setName(STATION_FROM_NAME);

        Passenger registeredPassenger = new Passenger();
        registeredPassenger.setId(REGISTERED_PASSENGER_ID);
        registeredPassenger.setName(REGISTERED_PASSENGER_NAME);
        registeredPassenger.setSurname(REGISTERED_PASSENGER_SURNAME);
        registeredPassenger.setBirthdate(REGISTERED_PASSENGER_BIRTHDATE);

        StationDao stationDao = mock(StationDao.class);
        ScheduleDao scheduleDao = mock(ScheduleDao.class);
        TrainDao trainDao = mock(TrainDao.class);
        TicketDao ticketDao = mock(TicketDao.class);
        PassengerDao passengerDao = mock(PassengerDao.class);

        when(stationDao.getStation(STATION_FROM_NAME)).thenReturn(station);
        when(stationDao.getStation(NOT_EXIST_STATION)).thenReturn(null);

        when(scheduleDao.getTrainId(TRAIN_NUMBER, STATION_ID, SCHEDULE_DEPARTURE_DATE)).thenReturn(TRAIN_ID);
        when(scheduleDao.getTrainId(TRAIN_NUMBER, STATION_ID, IN_TIME_DEPARTURE_DATE_TIME)).thenReturn(TRAIN_ID);
        when(scheduleDao.getTrainId(TRAIN_NUMBER, STATION_ID, SALE_STOP_DEPARTURE_DATE_TIME)).thenReturn(TRAIN_ID);
        when(scheduleDao.getTrainId(FULL_TRAIN_NUMBER, STATION_ID, SCHEDULE_DEPARTURE_DATE)).thenReturn
                (FULL_TRAIN_ID);
        when(scheduleDao.getTrainId(STOP_SALE_TRAIN_NUMBER, STATION_ID, SALE_STOP_DEPARTURE_DATE_TIME)).thenReturn
                (STOP_SALE_TRAIN_ID);
        when(scheduleDao.getTrainId(NOT_EXIST_TRAIN_NUMBER, STATION_ID, SCHEDULE_DEPARTURE_DATE)).thenReturn(null);

        when(trainDao.read(TRAIN_ID)).thenReturn(train);
        when(trainDao.read(FULL_TRAIN_ID)).thenReturn(fullTrain);
        when(trainDao.read(STOP_SALE_TRAIN_ID)).thenReturn(stopSaleTrain);

        when(ticketDao.hasBeenRegistered(TRAIN_ID, REGISTERED_PASSENGER_ID)).thenReturn(true);
        when(ticketDao.countOfTickets(TRAIN_ID)).thenReturn(TRAIN_SEATS - 1);
        when(ticketDao.countOfTickets(FULL_TRAIN_ID)).thenReturn(FULL_TRAIN_SEATS);
        when(ticketDao.countOfTickets(STOP_SALE_TRAIN_ID)).thenReturn(STOP_SALE_TRAIN_ID);

        when(scheduleDao.getDepartureDate(TRAIN_ID, STATION_ID)).thenReturn(SCHEDULE_DEPARTURE_DATE);
        when(scheduleDao.getDepartureDate(FULL_TRAIN_ID, STATION_ID)).thenReturn(SCHEDULE_DEPARTURE_DATE);
        when(scheduleDao.getDepartureDate(STOP_SALE_TRAIN_ID, STATION_ID)).thenReturn(SALE_STOP_DEPARTURE_DATE_TIME);

        when(passengerDao.getPassenger(anyString(), anyString(), notNull(Date.class))).thenReturn(null);
        when(passengerDao.getPassenger(REGISTERED_PASSENGER_NAME, REGISTERED_PASSENGER_SURNAME, REGISTERED_PASSENGER_BIRTHDATE))
                .thenReturn(registeredPassenger);

        DaoContext context = new HibernateDaoContext();
        context.put(TrainDao.class, trainDao);
        context.put(ScheduleDao.class, scheduleDao);
        context.put(StationDao.class, stationDao);
        context.put(TicketDao.class, ticketDao);
        context.put(PassengerDao.class, passengerDao);

        ticketService = new TicketServiceImpl(context);

        passengerData = new PassengerData();
        passengerData.setName("");
        passengerData.setSurname("");
        passengerData.setBirthdate(new Date());
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNotExistStation() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION, passengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithEmptyPassengerData() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION, new PassengerData());
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullNameInPassengerData() throws Exception {
        PassengerData invalidPassengerData = new PassengerData();
        invalidPassengerData.setName(null);
        invalidPassengerData.setSurname("");
        invalidPassengerData.setBirthdate(new Date());
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION,
                invalidPassengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullSurnameInPassengerData() throws Exception {
        PassengerData invalidPassengerData = new PassengerData();
        invalidPassengerData.setName("");
        invalidPassengerData.setSurname(null);
        invalidPassengerData.setBirthdate(new Date());
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION,
                invalidPassengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullBirthdateInPassengerData() throws Exception {
        PassengerData invalidPassengerData = new PassengerData();
        invalidPassengerData.setName("");
        invalidPassengerData.setSurname("");
        invalidPassengerData.setBirthdate(null);
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION,
                invalidPassengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNotExistPassengerData() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, NOT_EXIST_STATION, null);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullStation() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, null, passengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullDate() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, null, STATION_FROM_NAME, passengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNullStationAndDate() throws Exception {
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, null, null, passengerData);
    }

    @Test(expected = InvalidInputDataException.class)
    public void testBuyTicketWithNotExistTrainNumber() throws Exception {
        ticketService.buyTicket(USER_ID, NOT_EXIST_TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, STATION_FROM_NAME,
                passengerData);
    }

    @Test(expected = AlreadyRegisteredException.class)
    public void testBuyTicketAlreadyRegistered() throws Exception {
        PassengerData registeredPassenger = new PassengerData();
        registeredPassenger.setName(REGISTERED_PASSENGER_NAME);
        registeredPassenger.setSurname(REGISTERED_PASSENGER_SURNAME);
        registeredPassenger.setBirthdate(REGISTERED_PASSENGER_BIRTHDATE);
        ticketService.buyTicket(USER_ID, TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, STATION_FROM_NAME, registeredPassenger);
    }

    @Test(expected = HasNoEmptySeatsException.class)
    public void testBuyTicketHasNoEmptySeats() throws Exception {
        ticketService.buyTicket(USER_ID, FULL_TRAIN_NUMBER, SCHEDULE_DEPARTURE_DATE, STATION_FROM_NAME, passengerData);
    }
}