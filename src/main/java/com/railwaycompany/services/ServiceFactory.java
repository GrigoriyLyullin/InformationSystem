package com.railwaycompany.services;

public interface ServiceFactory {

    AuthenticationService getAuthenticationService();

    StationService getStationService();

    ScheduleService getScheduleService();

    void close();
}
