package com.railwaycompany.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * DaoFactory implementation for Hibernate.
 */
public class HibernateDaoFactory implements DaoFactory {

    /**
     * Persistence unit name from persistence.xml.
     */
    private static final String PERSISTENCE_UNIT_NAME = "RailwayInformationSystem";

    /**
     * Factory that creates EntityManager object.
     */
    private final EntityManagerFactory entityManagerFactory;

    /**
     * Interface used to interact with the persistence context.
     */
    private final EntityManager entityManager;

    private UserDao userDao;

    private StationDao stationDao;

    /**
     * HibernateDaoFactory constructor.
     *
     * @param persistenceUnitName - persistence unit name from persistence.xml.
     */
    public HibernateDaoFactory(String persistenceUnitName) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.entityManager = entityManagerFactory.createEntityManager();
        userDao = new UserHibernateDao(entityManager);
        stationDao = new StationHibernateDao(entityManager);
    }

    /**
     * HibernateDaoFactory constructor. Creates HibernateDaoFactory with default persistence unit name.
     */
    public HibernateDaoFactory() {
        this(PERSISTENCE_UNIT_NAME);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public StationDao getStationDao() {
        return stationDao;
    }

    @Override
    public void close() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
