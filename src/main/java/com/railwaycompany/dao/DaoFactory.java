package com.railwaycompany.dao;

/**
 * DaoFactory interface. Creates Factory objects for work with the database.
 */
public interface DaoFactory {

    /**
     * Returns HibernateDao for working with User entities.
     *
     * @return UserHibernateDao object.
     */
    UserHibernateDao getUserHibernateDao();

    /**
     * Closes all object (Streams and e.g.) that should will be closed. Call this before object destroying.
     */
    void close();
}
