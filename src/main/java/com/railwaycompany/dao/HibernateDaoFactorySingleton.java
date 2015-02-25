package com.railwaycompany.dao;

public class HibernateDaoFactorySingleton {

    private static HibernateDaoFactory hibernateDaoFactory = new HibernateDaoFactory();

    public static HibernateDaoFactory getInstance() {
        return hibernateDaoFactory;
    }

    private HibernateDaoFactorySingleton() {
    }
}
