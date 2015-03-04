package com.railwaycompany.dao.hibernateDao;

import com.railwaycompany.dao.abstractDao.DaoFactory;

public class HibernateDaoFactorySingleton {

    private static DaoFactory hibernateDaoFactory = new HibernateDaoFactory();

    public static DaoFactory getInstance() {
        return hibernateDaoFactory;
    }

    private HibernateDaoFactorySingleton() {
    }
}
