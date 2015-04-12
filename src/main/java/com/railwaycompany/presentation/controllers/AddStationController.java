package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.utils.ValidationHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("add_station")
public class AddStationController {

    private static final Logger LOG = Logger.getLogger(AddStationController.class);

    private static final String GET_ALL_STATION_PARAM = "getAllStation";
    private static final String STATION_NAME_PARAM = "stationName";
    private static final String ALL_STATION_LIST_ATTR = "allStationList";
    private static final String EXIST_STATION_ERROR_ATTR = "existStationError";
    private static final String EXIST_STATION_NAME_ATTR = "existStationName";
    private static final String INVALID_STATION_NAME_ATTR = "invalidStationNameError";

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpServletRequest request, HttpSession session) {
        List<StationData> allStationList = null;
        Boolean getAll = Boolean.valueOf(request.getParameter(GET_ALL_STATION_PARAM));
        if (getAll) {
            allStationList = stationService.getAll();
        }
        session.setAttribute(ALL_STATION_LIST_ATTR, allStationList);
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(HttpServletRequest request, HttpSession session) {
        String stationName = request.getParameter(STATION_NAME_PARAM);
        boolean allStationListExist = (session.getAttribute(ALL_STATION_LIST_ATTR) != null);

        if (ValidationHelper.isValidStationName(stationName)) {
            try {
                stationService.addStation(stationName);
                if (allStationListExist) {
                    session.setAttribute(ALL_STATION_LIST_ATTR, stationService.getAll());
                }
            } catch (StationWithSuchNameExistException e) {
                session.setAttribute(EXIST_STATION_ERROR_ATTR, true);
                session.setAttribute(EXIST_STATION_NAME_ATTR, stationName);
                LOG.warn("Station with such name does not exist. Station name: " + stationName);
            }
        } else {
            session.setAttribute(INVALID_STATION_NAME_ATTR, true);
            LOG.warn("Invalid station name: " + stationName);
        }
        return "employee_page";
    }
}
