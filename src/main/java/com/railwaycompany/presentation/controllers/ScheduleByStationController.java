package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.utils.ValidationHelper;
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

@Controller
@RequestMapping("schedule_by_station")
public class ScheduleByStationController {

    private static final Logger LOG = Logger.getLogger(ScheduleByStationController.class.getName());

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public String getScheduleByStationPage() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@RequestParam(value = "schedule-by-station-name") String stationName,
                         @RequestParam(value = "step-size", defaultValue = "3") int stepSize,
                         @RequestParam(value = "start-number", defaultValue = "0") int startNumber,
                         HttpSession session) {
        if (ValidationHelper.isValidStationName(stationName)) {
            session.setAttribute("scheduleByStationName", stationName);
            List<ScheduleData> scheduleOfTrainsByStation = scheduleService.getSchedule(stationName, new Date(), stepSize, startNumber);
            if (scheduleOfTrainsByStation != null) {
                if (session.getAttribute("maxSize") == null) {
                    int maxSize = scheduleService.getSchedule(stationName, new Date()).size() - 1;
                    session.setAttribute("maxSize", maxSize);
                }
                session.setAttribute("stepSize", stepSize);
                session.setAttribute("startNumber", startNumber);
                session.setAttribute("scheduleDataList", scheduleOfTrainsByStation);
                session.setAttribute("scheduleByStationNotFound", false);
            } else {
                session.setAttribute("scheduleDataList", null);
                session.setAttribute("scheduleByStationNotFound", true);
            }
        } else {
            LOG.log(Level.WARNING, "Invalid station name: " + stationName);
        }
        return "index";
    }
}
