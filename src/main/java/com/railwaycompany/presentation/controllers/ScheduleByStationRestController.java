package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.business.services.interfaces.StationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("schedule_by_station/rest")
public class ScheduleByStationRestController {

    private static final Logger LOG = Logger.getLogger(ScheduleByStationRestController.class.getName());

    private static final int STEP_SIZE = 3;

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ScheduleData> getSchedule(@RequestParam(value = "schedule-by-station-name") String stationName,
                                          @RequestParam(value = "schedule-by-station-start-number",
                                                  defaultValue = "0") int startNumber,
                                          HttpServletResponse response) {
        List<ScheduleData> scheduleOfTrainsByStation = null;
        if (stationService.exist(stationName)) {
            response.setStatus(HttpServletResponse.SC_OK);
            scheduleOfTrainsByStation = scheduleService.
                    getSchedule(stationName, new Date(), STEP_SIZE, startNumber);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            LOG.warn("Station does not exist. Station name: " + stationName);
        }
        return scheduleOfTrainsByStation;
    }

    @RequestMapping(value = "maxSize", method = RequestMethod.GET)
    public int getSchedule(@RequestParam(value = "schedule-by-station-name") String stationName) {
        if (stationService.exist(stationName)) {
            return scheduleService.getSchedule(stationName, new Date()).size() - 1;
        }
        return 0;
    }
}
