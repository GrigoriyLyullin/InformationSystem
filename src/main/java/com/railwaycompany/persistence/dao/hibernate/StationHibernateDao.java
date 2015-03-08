package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;

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
    public Station getStation(String name) {

        Query query = entityManager.createQuery(FIND_STATION_BY_NAME);
        query.setParameter("name", name);

        Station station = null;
        try {
            station = (Station) query.getSingleResult();
        } catch (NoResultException e) {
            String message = "No station was found for name: \"" + name + "\"";
            log.log(Level.INFO, message);
        } catch (ClassCastException e) {
            String message = "Query returns not Station object.";
            log.log(Level.WARNING, message, e);
        }
        return station;
    }
}
