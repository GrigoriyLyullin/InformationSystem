package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.services.abstractServices.ServiceFactory;

public class ServiceFactorySingleton {

    private static ServiceFactory serviceFactory = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    private ServiceFactorySingleton() {
    }
}
