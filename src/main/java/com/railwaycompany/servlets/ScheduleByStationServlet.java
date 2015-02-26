package com.railwaycompany.servlets;

import com.railwaycompany.services.ScheduleService;
import com.railwaycompany.services.ServiceFactory;
import com.railwaycompany.services.ServiceFactorySingleton;
import com.railwaycompany.services.StationService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class ScheduleByStationServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(ScheduleByStationServlet.class.getName());

    private ScheduleService scheduleService = new ScheduleService();

    @Override
    public void init() throws ServletException {
        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
//        scheduleService  = serviceFactory.getAuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/schedule_by_station.html");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationName = req.getParameter("Station-name");

        log.info("stationName: " + stationName);

        if (stationName != null && !stationName.isEmpty()) {

            String scheduleOfTrainsByStation = scheduleService.getScheduleByStationName(stationName);
            if (scheduleOfTrainsByStation != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(scheduleOfTrainsByStation);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("error");
            }
        } else {
            resp.sendRedirect("/error.html");
        }
    }

}
