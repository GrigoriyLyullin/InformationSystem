package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.ScheduleDao;
import com.railwaycompany.persistence.entities.Schedule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class JdbcScheduleDao implements GenericDAO<Schedule>, ScheduleDao {

    private static final Logger LOG = Logger.getLogger(JdbcScheduleDao.class.getName());

    @Autowired
    private JdbcConnection connection;

    @Override
    public List<Schedule> getSchedulesByStationId(int stationId) {
        LOG.warn("Unsupported operation List<Schedule> getSchedulesByStationId(int stationId)");
        return null;
    }

    @Override
    public List<Schedule> getSchedules(int stationId, Date departureDate) {
        LOG.warn("Unsupported operation List<Schedule> getSchedules(int stationId, Date departureDate)");
        return null;
    }

    @Override
    public List<Schedule> getSchedules(Date arrivalDate, int stationId) {
        LOG.warn("Unsupported operation List<Schedule> getSchedules(Date arrivalDate, int stationId)");
        return null;
    }

    @Override
    public List<Schedule> getSchedules(int stationId, int trainId) {
        LOG.warn("Unsupported operation List<Schedule> getSchedules(int stationId, int trainId)");
        return null;
    }

    @Override
    public List<Schedule> getSchedulesByTrainId(int trainId) {
        LOG.warn("Unsupported operation List<Schedule> getSchedulesByTrainId(int trainId)");
        return null;
    }

    @Override
    public Date getDepartureDate(int trainId, int stationId) {
        LOG.warn("Unsupported operation Date getDepartureDate(int trainId, int stationId)");
        return null;
    }

    @Override
    public Integer getTrainId(int trainNumber, int stationId, Date departureDate) {
        LOG.warn("Unsupported operation Integer getTrainId(int trainNumber, int stationId, Date departureDate)");
        return null;
    }

    @Override
    public List<Schedule> getSchedules(int stationId, Date departureDateFrom, Date departureDateTo) {
        LOG.warn("Unsupported operation List<Schedule> getSchedules(int stationId, Date departureDateFrom," +
                " Date departureDateTo)");
        return null;
    }

    @Override
    public List<ScheduleData> getSchedules(int stationFromId, int stationToId, Date departureDate) {
        LOG.warn("Unsupported operation List<ScheduleData> getSchedules(int stationFromId, int stationToId," +
                " Date departureDate)");
        return null;
    }

    @Override
    public Schedule getSchedule(int stationId, int trainId, Date arrivalDate, Date departureDate) {
        LOG.warn("Unsupported operation Schedule getSchedule(int stationId, int trainId, Date arrivalDate," +
                " Date departureDate)");
        return null;
    }

    @Override
    public List<Schedule> getSchedules(Date dateFrom, Date dateTo) {
        LOG.warn("Unsupported operation List<Schedule> getSchedules(Date dateFrom, Date dateTo)");
        return null;
    }

    @Override
    public void create(Schedule schedule) {
        LOG.warn("Unsupported operation create(Schedule schedule)");
    }

    @Override
    public Schedule read(int key) {
        LOG.warn("Unsupported operation Schedule read(int key)");
        return null;
    }

    @Override
    public Schedule update(Schedule schedule) {
        LOG.warn("Unsupported operation Schedule update(Schedule schedule)");
        return null;
    }

    @Override
    public void delete(Schedule schedule) {
        LOG.warn("Unsupported operation delete(Schedule schedule)");
    }
}
