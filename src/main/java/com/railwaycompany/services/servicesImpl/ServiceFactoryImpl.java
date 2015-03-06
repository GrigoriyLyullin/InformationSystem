package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.*;
import com.railwaycompany.dao.hibernateDao.HibernateDaoFactorySingleton;
import com.railwaycompany.services.abstractServices.*;

public class ServiceFactoryImpl implements ServiceFactory {

    private DaoFactory daoFactory;

    private AuthenticationService authenticationService;

    private StationService stationService;

    private ScheduleService scheduleService;

    private TrainService trainService;

    private UserService userService;

    private TicketService ticketService;

    public ServiceFactoryImpl() {
        this.daoFactory = HibernateDaoFactorySingleton.getInstance();

        UserDao userDao = daoFactory.getUserDao();
        StationDao stationDao = daoFactory.getStationDao();
        ScheduleDao scheduleDao = daoFactory.getScheduleDao();
        TrainDao trainDao = daoFactory.getTrainDao();
        TicketDao ticketDao = daoFactory.getTicketDao();

        authenticationService = new AuthenticationServiceImpl(userDao);
        stationService = new StationServiceImpl(stationDao);
        scheduleService = new ScheduleServiceImpl(scheduleDao);
        trainService = new TrainServiceImpl(trainDao, ticketDao, stationDao, scheduleDao);
        userService = new UserServiceImpl(userDao);
        ticketService = new TicketServiceImpl(ticketDao);
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
    public void close() {
        daoFactory.close();
    }
}
