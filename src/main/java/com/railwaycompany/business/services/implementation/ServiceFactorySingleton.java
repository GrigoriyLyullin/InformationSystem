package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.ServiceFactory;

public class ServiceFactorySingleton {

    private static ServiceFactory serviceFactory = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    private ServiceFactorySingleton() {
    }
}
