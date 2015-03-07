package com.railwaycompany.presentation.servlets;

import com.railwaycompany.persistence.entities.Station;
import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.business.services.interfaces.ServiceFactory;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.utils.DateHelper;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class SearchTrainServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(SearchTrainServlet.class.getName());

    private ScheduleService scheduleService;
    private StationService stationService;

    @Override
    public void init() throws ServletException {
        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
        scheduleService = serviceFactory.getScheduleService();
        stationService = serviceFactory.getStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("extendedForm", Boolean.valueOf(req.getParameter("extendedForm")));
        resp.sendRedirect("/#search_train");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationFromName = req.getParameter("Station-From-Name");
        String stationToName = req.getParameter("Station-To-Name");
        String dateFromStr = req.getParameter("dateFrom");
        String dateToStr = req.getParameter("dateTo");
        String timeFromStr = req.getParameter("timeFrom");
        String timeToStr = req.getParameter("timeTo");

        log.info("stationFrom: " + stationFromName + " stationTo: " + stationToName + " dateFrom: " +
                dateFromStr + " timeFrom: " + timeFromStr + " dateTo: " + dateToStr + " timeTo: " + timeToStr);

        HttpSession session = req.getSession();

        session.setAttribute("stationFromName", stationFromName);
        session.setAttribute("stationToName", stationToName);

        session.setAttribute("dateFrom", dateFromStr);
        session.setAttribute("dateTo", dateToStr);

//        session.setAttribute("trainNotFound", true);
//        session.setAttribute("trainList", null);

        boolean validStationFromName = ValidationHelper.isValidStationName(stationFromName);
        boolean validStationToName = ValidationHelper.isValidStationName(stationToName);
        boolean validDateFromStr = ValidationHelper.isValidDateStr(dateFromStr);
        boolean validDateToStr = ValidationHelper.isValidDateStr(dateToStr);
        boolean validTimeFromStr = ValidationHelper.isValidTimeStr(timeFromStr);
        boolean validTimeToStr = ValidationHelper.isValidTimeStr(timeToStr);

        //Simple train search
        if (validStationFromName && validStationToName && validDateFromStr) {
            List<ScheduleData> scheduleList;
            Date dateFrom = DateHelper.convertDate(dateFromStr);
            if (validDateToStr) {
                Date dateTo = DateHelper.convertDate(dateToStr);
                if (validTimeFromStr) {
                    dateFrom = DateHelper.convertDatetime(dateFromStr + " " + timeFromStr);
                }
                if (validTimeToStr) {
                    dateTo = DateHelper.convertDatetime(dateToStr + " " + timeToStr);
                }
                Station stationFrom = stationService.getStation(stationFromName);
                Station stationTo = stationService.getStation(stationToName);
                scheduleList = scheduleService.getSchedule(stationFrom, stationTo, dateFrom, dateTo);
            } else {
                Station stationFrom = stationService.getStation(stationFromName);
                Station stationTo = stationService.getStation(stationToName);
                scheduleList = scheduleService.getSchedule(stationFrom, stationTo, dateFrom);
            }

            session.setAttribute("trainSearchingError", false);

            if (scheduleList != null) {
                session.setAttribute("trainList", scheduleList);
            } else {
                session.setAttribute("trainNotFoundError", true);
            }
        }
        resp.sendRedirect("/#search_train");
    }
}
