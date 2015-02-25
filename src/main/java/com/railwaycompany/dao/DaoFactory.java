package com.railwaycompany.dao;

/**
 * DaoFactory interface. Creates Factory objects for work with the database.
 */
public interface DaoFactory {

    /**
     * Returns HibernateDao for working with User entities.
     *
     * @return UserDao object.
     */
    UserDao getUserDao();

    /**
     * Returns HibernateDao for working with Station entities.
     *
     * @return StationDao object.
     */
    StationDao getStationDao();

    /**
     * Closes all object (Streams and e.g.) that should will be closed. Call this before object destroying.
     */
    void close();
}
