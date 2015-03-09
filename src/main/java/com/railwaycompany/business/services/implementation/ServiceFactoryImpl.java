package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.*;
import com.railwaycompany.persistence.dao.hibernate.HibernateDaoContext;
import com.railwaycompany.persistence.dao.hibernate.HibernateDaoFactorySingleton;
import com.railwaycompany.persistence.dao.interfaces.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private DaoFactory daoFactory;

    private AuthenticationService authenticationService;

    private StationService stationService;

    private ScheduleService scheduleService;

    private TrainService trainService;

    private UserService userService;

    private TicketService ticketService;

    private PassengerService passengerService;

    public ServiceFactoryImpl() {
        this.daoFactory = HibernateDaoFactorySingleton.getInstance();

        UserDao userDao = daoFactory.getUserDao();
        StationDao stationDao = daoFactory.getStationDao();
        ScheduleDao scheduleDao = daoFactory.getScheduleDao();
        TrainDao trainDao = daoFactory.getTrainDao();
        TicketDao ticketDao = daoFactory.getTicketDao();
        PassengerDao passengerDao = daoFactory.getPassengerDao();

        DaoContext daoContext = new HibernateDaoContext();
        daoContext.put(UserDao.class, userDao);
        daoContext.put(StationDao.class, stationDao);
        daoContext.put(ScheduleDao.class, scheduleDao);
        daoContext.put(TrainDao.class, trainDao);
        daoContext.put(TicketDao.class, ticketDao);
        daoContext.put(PassengerDao.class, passengerDao);

        authenticationService = new AuthenticationServiceImpl(daoContext);
        stationService = new StationServiceImpl(daoContext);
        scheduleService = new ScheduleServiceImpl(daoContext);
        trainService = new TrainServiceImpl(daoContext);
        userService = new UserServiceImpl(daoContext);
        ticketService = new TicketServiceImpl(daoContext);
        passengerService = new PassengerServiceImpl(daoContext);
    }


    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @Override
    public StationService getStationService() {
        return stationService;
    }

    @Override
    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    @Override
    public TrainService getTrainService() {
        return trainService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public TicketService getTicketService() {
        return ticketService;
    }

    @Override
    public PassengerService getPassengerService() {
        return passengerService;
    }

    @Override
    public void close() {
        daoFactory.close();
    }
}
