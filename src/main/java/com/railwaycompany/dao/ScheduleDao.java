package com.railwaycompany.dao;

import com.railwaycompany.entities.Schedule;

import java.util.List;

/**
 * GenericDao<Schedule> interface for work with Schedule entities.
 */
public interface ScheduleDao {

    List<Schedule> getSchedulesByStationId(int stationId);
}
