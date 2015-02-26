package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;

public class ServiceFactoryImpl implements ServiceFactory {

    private DaoFactory daoFactory;

    private AuthenticationService authenticationService;

    private StationService stationService;

    public ServiceFactoryImpl() {
        this.daoFactory = HibernateDaoFactorySingleton.getInstance();
        authenticationService = new AuthenticationService();
        stationService = new StationService();
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
    public void close() {
        daoFactory.close();
    }
}
