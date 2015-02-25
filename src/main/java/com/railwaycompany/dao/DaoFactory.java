package com.railwaycompany.dao;

import javax.persistence.EntityManager;

/**
 * DaoFactory interface. Creates Factory objects for work with the database.
 */
public interface DaoFactory {

    /**
     * Returns EntityManager - interface used to interact with the persistence context.
     *
     * @return EntityManager object
     */
    EntityManager getEntityManager();

    /**
     * Returns HibernateDao for working with User entities.
     *
     * @return UserHibernateDao object.
     */
    UserHibernateDao getUserHibernateDao();
}
