package com.railwaycompany.dao;

import com.railwaycompany.entities.Station;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StationHibernateDao extends HibernateDao<Station> implements StationDao {

    private static final String FIND_STATION_BY_NAME = "SELECT s FROM Station s WHERE s.name = :name";

    /**
     * Logger for StationHibernateDao class.
     */
    private static Logger log = Logger.getLogger(StationHibernateDao.class.getName());

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public StationHibernateDao(EntityManager entityManager) {
        super(entityManager, Station.class);
    }

    @Override
    public Station findStation(String name) {


        Query query = entityManager.createQuery(FIND_STATION_BY_NAME);
        query.setParameter("name", name);

        Station station = null;
        try {
            station = (Station) query.getSingleResult();
        } catch (NoResultException e) {
            log.log(Level.INFO, "No station was found for name: \"" + name + "\"");
        } catch (ClassCastException e) {
            log.log(Level.WARNING, "Query returns not Station object.", e);
        }

        return station;
    }
}
