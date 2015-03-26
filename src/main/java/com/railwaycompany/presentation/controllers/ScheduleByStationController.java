package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@RequestMapping("schedule_by_station")
public class ScheduleByStationController {

    private static final String STATION_NAME_ATTR = "Station-Name";
    private static final String EXTENDED_FORM_PARAM = "extendedForm";
    private static final String EXTENDED_SEARCH_ATTR = "extendedSearchByStationForm";
    private static final String STATION_NAME_ATTR1 = "stationName";
    private static final String SCHEDULE_LIST_ATTR = "scheduleList";
    private static final String STATION_NOT_FOUND_ATTR = "stationNotFound";


    private static final Logger LOG = Logger.getLogger(ScheduleByStationController.class.getName());

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest req) {
        req.getSession().setAttribute(EXTENDED_SEARCH_ATTR, Boolean.valueOf(req.getParameter(EXTENDED_FORM_PARAM)));
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest req) {
        String stationName = req.getParameter(STATION_NAME_ATTR);
        if (ValidationHelper.isValidStationName(stationName)) {
            HttpSession session = req.getSession();
            session.setAttribute(STATION_NAME_ATTR1, stationName);
            List<ScheduleData> scheduleOfTrainsByStation = scheduleService.getSchedule(stationName);
            if (scheduleOfTrainsByStation != null) {
                session.setAttribute(SCHEDULE_LIST_ATTR, scheduleOfTrainsByStation);
                session.setAttribute(STATION_NOT_FOUND_ATTR, false);
            } else {
                session.setAttribute(SCHEDULE_LIST_ATTR, null);
                session.setAttribute(STATION_NOT_FOUND_ATTR, true);
            }
        } else {
            LOG.log(Level.WARNING, "Invalid station name: " + stationName);
        }
        return "index";
    }
}
