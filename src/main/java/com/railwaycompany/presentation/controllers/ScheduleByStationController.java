package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.railwaycompany.utils.ValidationHelper.isValidStationName;

@Controller
@RequestMapping("schedule_by_station")
public class ScheduleByStationController {

    private static final Logger LOG = Logger.getLogger(ScheduleByStationController.class.getName());

    private static final String STEP_SIZE = "3";

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public String getScheduleByStationPage(@RequestParam(value = "schedule-by-station-name", required = false)
                                           String stationName, HttpSession session) {
        if (stationName != null) {
            getSchedulesByStation(stationName, Integer.valueOf(STEP_SIZE), 0, Direction.NEXT, session);
        }
        return "index";
    }

    @RequestMapping(value = "next", method = RequestMethod.GET)
    public String getNextSchedulesByStation(@RequestParam(value = "schedule-by-station-name") String stationName,
                                            @RequestParam(value = "schedule-by-station-step-size",
                                                    defaultValue = STEP_SIZE) int stepSize,
                                            @RequestParam(value = "schedule-by-station-start-number",
                                                    defaultValue = "0") int startNumber,
                                            HttpSession session) {
        getSchedulesByStation(stationName, stepSize, startNumber, Direction.NEXT, session);
        return "index";
    }

    @RequestMapping(value = "previous", method = RequestMethod.GET)
    public String getPrevSchedulesByStation(@RequestParam(value = "schedule-by-station-name") String stationName,
                                            @RequestParam(value = "schedule-by-station-step-size", defaultValue = STEP_SIZE) int stepSize,
                                            @RequestParam(value = "schedule-by-station-start-number", defaultValue = "0") int startNumber,
                                            HttpSession session) {
        getSchedulesByStation(stationName, stepSize, startNumber, Direction.PREVIOUS, session);
        return "index";
    }

    private void getSchedulesByStation(String stationName, int stepSize, int startNumber, Direction direction,
                                       HttpSession session) {
        if (isValidStationName(stationName)) {

            int maxSize;
            Object maxSizeAttr = session.getAttribute("scheduleByStationMaxSize");
            if (maxSizeAttr == null) {
                maxSize = scheduleService.getSchedule(stationName, new Date()).size() - 1;
                session.setAttribute("scheduleByStationMaxSize", maxSize);
            } else {
                maxSize = (int) maxSizeAttr;
            }

            if (direction == Direction.PREVIOUS) {
                startNumber = (startNumber >= stepSize) ? startNumber - stepSize : 0;
            } else if (direction == Direction.NEXT) {
                startNumber = (startNumber < maxSize) ? startNumber + stepSize : maxSize;
            }

            session.setAttribute("scheduleByStationName", stationName);
            List<ScheduleData> scheduleOfTrainsByStation = scheduleService.
                    getSchedule(stationName, new Date(), stepSize, startNumber);
            if (scheduleOfTrainsByStation != null) {


                session.setAttribute("scheduleByStationStartNumber", startNumber);
                session.setAttribute("scheduleByStationDataList", scheduleOfTrainsByStation);
                session.setAttribute("scheduleByStationNotFound", false);
            } else {
                session.setAttribute("scheduleByStationDataList", null);
                session.setAttribute("scheduleByStationNotFound", true);
            }
        } else {
            LOG.log(Level.WARNING, "Invalid station name: " + stationName);
        }
    }

    enum Direction {
        PREVIOUS,
        NEXT
    }
}
