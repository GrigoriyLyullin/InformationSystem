package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.HibernateDaoFactorySingleton;
import com.railwaycompany.dao.ScheduleDao;
import com.railwaycompany.entities.Schedule;
import com.railwaycompany.entities.Station;
import com.railwaycompany.entities.Train;

import java.util.List;
import java.util.logging.Logger;

public class ScheduleService {

    private static Logger log = Logger.getLogger(AuthenticationService.class.getName());

    private DaoFactory daoFactory;

    private ScheduleDao scheduleDao;

    public ScheduleService() {
        daoFactory = HibernateDaoFactorySingleton.getInstance();
        scheduleDao = daoFactory.getScheduleDao();
    }

    public String getScheduleByStationName(String name) {

        StationService stationService = new StationService();

        String result = null;

        Station station = stationService.getStation(name);
        if (station != null) {

            List<Schedule> schedulesByStationId = scheduleDao.getSchedulesByStationId(station.getId());

            StringBuilder builder = new StringBuilder();
            if (!schedulesByStationId.isEmpty()) {
                for (Schedule s : schedulesByStationId) {
                    Train train = s.getTrain();
                    builder.append("Train: ").append(train.getNumber()).append(" TimeArrival: ").append(s
                            .getTimeArrival()).append(" TimeDeparture: ").append(s.getTimeDeparture());
                }
                result = builder.toString();
            } else {
                result = "Empty!";
            }
        }

        if (result == null) {
            result = "Fatal error";
        }

        log.info("Result: " + result);

        return result;
    }
}
