package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.DaoFactory;

public class HibernateDaoFactorySingleton {

    private static DaoFactory hibernateDaoFactory = new HibernateDaoFactory();

    public static DaoFactory getInstance() {
        return hibernateDaoFactory;
    }

    private HibernateDaoFactorySingleton() {
    }
}
