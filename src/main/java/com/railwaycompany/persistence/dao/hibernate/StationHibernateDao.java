package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StationHibernateDao extends HibernateDao<Station> implements StationDao {

    private static final String FIND_STATION_BY_NAME = "SELECT s FROM Station s WHERE s.name = :name";

    private static final String SELECT_ALL_STATIONS = "SELECT s FROM Station s";

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

    @Override
    public List<Station> getAll() {

        Query query = entityManager.createQuery(SELECT_ALL_STATIONS);

        List<Station> stationList = null;
        try {
            List resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                stationList = new ArrayList<>();
                for (Object o : resultList) {
                    if (o instanceof Station) {
                        stationList.add((Station) o);
                    }
                }
            }

        } catch (NoResultException e) {
            String message = "No stations was found";
            log.log(Level.INFO, message);
        } catch (ClassCastException e) {
            String message = "Query returns not Station object.";
            log.log(Level.WARNING, message, e);
        }
        return stationList;
    }
}
