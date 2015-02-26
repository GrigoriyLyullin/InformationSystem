package com.railwaycompany.services;

public interface ServiceFactory {

    AuthenticationService getAuthenticationService();

    StationService getStationService();

    void close();

}
