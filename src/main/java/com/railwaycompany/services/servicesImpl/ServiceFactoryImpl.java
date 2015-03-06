package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.DaoFactory;
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
        authenticationService = new AuthenticationServiceImpl(daoFactory);
        stationService = new StationServiceImpl(daoFactory);
        scheduleService = new ScheduleServiceImpl(daoFactory);
        trainService = new TrainServiceImpl(daoFactory);
        userService = new UserServiceImpl(daoFactory);
        ticketService = new TicketServiceImpl(daoFactory);
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
