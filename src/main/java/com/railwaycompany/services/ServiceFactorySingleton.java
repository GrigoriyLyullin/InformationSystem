package com.railwaycompany.services;

public class ServiceFactorySingleton {

    private static ServiceFactory serviceFactory = new ServiceFactoryImpl();

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    private ServiceFactorySingleton() {
    }
}
