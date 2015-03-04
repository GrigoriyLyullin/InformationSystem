package com.railwaycompany.servlets;

import com.railwaycompany.services.ScheduleByStation;
import com.railwaycompany.services.ScheduleService;
import com.railwaycompany.services.ServiceFactorySingleton;
import com.railwaycompany.utils.ValidationCheck;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ScheduleByStationServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(ScheduleByStationServlet.class.getName());

    private ScheduleService scheduleService;

    @Override
    public void init() throws ServletException {
        scheduleService = ServiceFactorySingleton.getInstance().getScheduleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("extendedForm", Boolean.valueOf(req.getParameter("extendedForm")));
        resp.sendRedirect("/#schedule_by_station");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationName = req.getParameter(ScheduleService.STATION_NAME_ATTR);
        log.info(ScheduleService.STATION_NAME_ATTR + " : " + stationName);

        if (ValidationCheck.isValidStationName(stationName)) {
            HttpSession session = req.getSession();
            session.setAttribute("stationName", stationName);
            List<ScheduleByStation> scheduleOfTrainsByStation = scheduleService.getScheduleByStationName(stationName);
            if (scheduleOfTrainsByStation != null) {
                session.setAttribute("scheduleList", scheduleOfTrainsByStation);
                session.setAttribute("stationNotFound", false);
            } else {
                session.setAttribute("scheduleList", null);
                session.setAttribute("stationNotFound", true);
            }
        }
        resp.sendRedirect("/#schedule_by_station");
    }
}
