package com.railwaycompany.dao;

public class HibernateDaoFactorySingleton {

    private static DaoFactory hibernateDaoFactory = new HibernateDaoFactory();

    public static DaoFactory getInstance() {
        return hibernateDaoFactory;
    }

    private HibernateDaoFactorySingleton() {
    }
}
