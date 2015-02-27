package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;

public class ServiceFactoryImpl implements ServiceFactory {

    private DaoFactory daoFactory;

    private AuthenticationService authenticationService;

    private StationService stationService;

    private ScheduleService scheduleService;

    public ServiceFactoryImpl() {
        this.daoFactory = HibernateDaoFactorySingleton.getInstance();
        authenticationService = new AuthenticationService(daoFactory);
        stationService = new StationService(daoFactory);
        scheduleService = new ScheduleService(daoFactory, stationService);
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
    public void close() {
        daoFactory.close();
    }
}
