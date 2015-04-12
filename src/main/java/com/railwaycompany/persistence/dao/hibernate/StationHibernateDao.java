package com.railwaycompany.persistence.dao.hibernate;

import com.railwaycompany.persistence.dao.interfaces.StationDao;
import com.railwaycompany.persistence.entities.Station;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StationHibernateDao extends HibernateDao<Station> implements StationDao {

    /**
     * Logger for StationHibernateDao class.
     */
    private static final Logger LOG = Logger.getLogger(StationHibernateDao.class.getName());

    private static final String FIND_STATION_BY_NAME = "SELECT s FROM Station s WHERE s.name = :name";

    private static final String SELECT_ALL_STATIONS = "SELECT s FROM Station s";

    /**
     * HibernateDao constructor.
     */
    public StationHibernateDao() {
        this.setEntityClass(Station.class);
    }

    @Override
    public Station getStation(String name) {

        Query query = entityManager.createQuery(FIND_STATION_BY_NAME);
        query.setParameter("name", name);

        Station station = null;
        try {
            station = (Station) query.getSingleResult();
        } catch (NoResultException | ClassCastException e) {
            LOG.warn(e);
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
        } catch (NoResultException | ClassCastException e) {
            LOG.warn(e);
        }
        return stationList;
    }
}
