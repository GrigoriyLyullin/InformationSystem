package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.business.services.interfaces.ServiceFactory;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScheduleByStationServlet extends HttpServlet {

    private static final String INDEX_PAGE = "/WEB-INF/index.jsp";
    private static final String STATION_NAME_ATTR = "Station-Name";
    private static final String EXTENDED_FORM_PARAM = "extendedForm";
    private static final String EXTENDED_SEARCH_ATTR = "extendedSearchByStationForm";
    private static final String STATION_NAME_ATTR1 = "stationName";
    private static final String SCHEDULE_LIST_ATTR = "scheduleList";
    private static final String STATION_NOT_FOUND_ATTR = "stationNotFound";


    private static final Logger LOG = Logger.getLogger(ScheduleByStationServlet.class.getName());

    private ScheduleService scheduleService;

    @Override
    public void init() throws ServletException {
        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
        scheduleService = serviceFactory.getScheduleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(EXTENDED_SEARCH_ATTR, Boolean.valueOf(req.getParameter(EXTENDED_FORM_PARAM)));
        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }
}
