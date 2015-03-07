package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Schedule;

import java.util.Date;
import java.util.List;

/**
 * GenericDAO<Schedule> interface for work with Schedule entities.
 */
public interface ScheduleDao {

    List<Schedule> getSchedules(int stationId);

    List<Schedule> getSchedules(int stationId, Date departureDate);

    List<Schedule> getSchedules(Date arrivalDate, int stationId);

    List<Schedule> getSchedules(int stationId, int trainId);
}
