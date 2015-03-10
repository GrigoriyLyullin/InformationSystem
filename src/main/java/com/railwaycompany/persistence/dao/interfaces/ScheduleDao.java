package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Schedule;

import java.util.Date;
import java.util.List;

/**
 * GenericDAO<Schedule> interface for work with Schedule entities.
 */
public interface ScheduleDao extends GenericDAO<Schedule> {

    List<Schedule> getSchedulesByStationId(int stationId);

    List<Schedule> getSchedules(int stationId, Date departureDate);

    List<Schedule> getSchedules(Date arrivalDate, int stationId);

    List<Schedule> getSchedules(int stationId, int trainId);

    List<Schedule> getSchedulesByTrainId(int trainId);

    Date getDepartureDate(int trainId, int stationId);


    Integer getTrainId(int trainNumber, int stationId, Date departureDate);

    List<Schedule> getSchedules(int stationId, Date departureDateFrom, Date departureDateTo);

    Schedule getSchedule(int stationId, int trainId, Date arrivalDate, Date departureDate);
}
