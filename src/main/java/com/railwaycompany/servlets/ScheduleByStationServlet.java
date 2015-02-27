package com.railwaycompany.servlets;

import com.railwaycompany.services.ScheduleByStation;
import com.railwaycompany.services.ScheduleService;
import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        resp.sendRedirect("/schedule_by_station.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationName = req.getParameter(ScheduleService.STATION_NAME_ATTR);
        log.info(ScheduleService.STATION_NAME_ATTR + " : " + stationName);

        if (isValidStationName(stationName)) {
            req.getSession().setAttribute("stationName", stationName);
            List<ScheduleByStation> scheduleOfTrainsByStation = scheduleService.getScheduleByStationName(stationName);
            if (scheduleOfTrainsByStation != null) {
                req.getSession().setAttribute("scheduleList", scheduleOfTrainsByStation);
            } else {
                req.getSession().setAttribute("stationNotFound", true);
            }
        }
        getServletContext().getRequestDispatcher("/schedule_by_station.jsp").forward(req, resp);
    }

    private boolean isValidStationName(String name) {
        return name != null && !name.isEmpty();
    }
}
