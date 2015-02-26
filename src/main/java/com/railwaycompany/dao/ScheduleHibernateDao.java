package com.railwaycompany.dao;

import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleHibernateDao extends HibernateDao<Station> implements ScheduleDao {

    /**
     * Logger for ScheduleHibernateDao class.
     */
    private static Logger log = Logger.getLogger(ScheduleHibernateDao.class.getName());

    private static final String SCHEDULES_WITH_STATION_ID = "SELECT s FROM Schedule s WHERE s.station.id = :stationId";

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public ScheduleHibernateDao(EntityManager entityManager) {
        super(entityManager, Station.class);
    }

    @Override
    public List<Schedule> getSchedulesByStationId(int stationId) {

        Query query = entityManager.createQuery(SCHEDULES_WITH_STATION_ID);

        query.setParameter("stationId", stationId);

        List<Schedule> schedules = null;
        try {
            List resultList = query.getResultList();
            if (resultList != null) {
                schedules = new ArrayList<>();
                for (Object o : resultList) {
                    if (o instanceof Schedule) {
                        schedules.add((Schedule) o);
                    }
                }
            }
        } catch (NoResultException e) {
            log.log(Level.INFO, "No schedule was found for stationId = \"" + stationId + "\"");
        }

        return schedules;
    }
}
