package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("add_station")
public class AddStationController {

    private static final String GET_ALL_STATION_PARAM = "getAllStation";
    private static final String STATION_NAME_PARAM = "stationName";
    private static final String ALL_STATION_LIST_ATTR = "allStationList";
    private static final String EXIST_STATION_ERROR_ATTR = "existStationError";
    private static final String EXIST_STATION_NAME_ATTR = "existStationName";
    private static final String INVALID_STATION_NAME_ATTR = "invalidStationNameError";

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest req) throws ServletException, IOException {
        List<StationData> allStationList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter(GET_ALL_STATION_PARAM));
        if (getAll) {
            allStationList = stationService.getAll();
        }
        req.getSession().setAttribute(ALL_STATION_LIST_ATTR, allStationList);
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest req) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String stationName = req.getParameter(STATION_NAME_PARAM);
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
            }
        } else {
            session.setAttribute(INVALID_STATION_NAME_ATTR, true);
        }
        return "employee_page";
    }
}
