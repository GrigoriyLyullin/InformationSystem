package com.railwaycompany.dao;

import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleHibernateDao extends HibernateDao<Station> implements ScheduleDao {

    /**
     * Logger for ScheduleHibernateDao class.
     */
    private static Logger log = Logger.getLogger(ScheduleHibernateDao.class.getName());
    private static final String SCHEDULES_WITH_STATION_ID = "SELECT s FROM Schedule s WHERE s.station.id = :stationId";

    private static final String SCHEDULES_STATION_DEPARTURE = "SELECT s FROM Schedule s WHERE s" + ".station" +
            ".id = :stationId AND s.timeDeparture >= :departureDate";

    private static final String SCHEDULES_STATION_ARRIVAL = "SELECT s FROM Schedule s WHERE s" + ".station" +
            ".id = :stationId AND s.timeArrival <= :arrivalDate";

    /**
     * HibernateDao constructor.
     *
     * @param entityManager - entity manager
     */
    public ScheduleHibernateDao(EntityManager entityManager) {
        super(entityManager, Station.class);
    }

    @Override
    public List<Schedule> getSchedules(int stationId) {

        Query query = entityManager.createQuery(SCHEDULES_WITH_STATION_ID);

        query.setParameter("stationId", stationId);

        List<Schedule> schedules = null;
        try {

            List resultList = query.getResultList();
            schedules = new ArrayList<>();
            for (Object s : resultList) {
                schedules.add((Schedule) s);
            }

        } catch (NoResultException e) {
            log.log(Level.INFO, "No schedule was found for stationId = \"" + stationId + "\"");
        }

        return schedules;
    }

    @Override
    public List<Schedule> getSchedules(int stationId, Date departureDate) {
        Query query = entityManager.createQuery(SCHEDULES_STATION_DEPARTURE);
        query.setParameter("stationId", stationId);
        query.setParameter("departureDate", departureDate);

        List<Schedule> schedules = null;
        try {
            List resultList = query.getResultList();
            schedules = new ArrayList<>();
            for (Object s : resultList) {
                schedules.add((Schedule) s);
            }
        } catch (NoResultException e) {
            log.log(Level.INFO, "No schedule was found for stationId: \"" + stationId + "\", departure date: \"" +
                    departureDate + "\"");
        }
        return schedules;
    }

    @Override
    public List<Schedule> getSchedules(Date arrivalDate, int stationId) {
        Query query = entityManager.createQuery(SCHEDULES_STATION_ARRIVAL);
        query.setParameter("arrivalDate", arrivalDate);
        query.setParameter("stationId", stationId);

        List<Schedule> schedules = null;
        try {
            List resultList = query.getResultList();
            schedules = new ArrayList<>();
            for (Object s : resultList) {
                schedules.add((Schedule) s);
            }
        } catch (NoResultException e) {
            log.log(Level.INFO, "No schedule was found for stationId: \"" + stationId + "\", arrival date: \"" +
                    arrivalDate + "\"");
        }
        return schedules;
    }
}
