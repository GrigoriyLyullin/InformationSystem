package com.railwaycompany.servlets;

import com.railwaycompany.services.ScheduleByStation;
import com.railwaycompany.services.ScheduleService;
import com.railwaycompany.services.ServiceFactorySingleton;
import com.railwaycompany.utils.DateHelper;
import com.railwaycompany.utils.ValidationCheck;

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

    @Override
    public void init() throws ServletException {
        scheduleService = ServiceFactorySingleton.getInstance().getScheduleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Boolean extendedForm = Boolean.valueOf(req.getParameter("extendedForm"));
        req.getSession().setAttribute("extendedForm", extendedForm);
        getServletContext().getRequestDispatcher("/search_train.jsp").forward(req, resp);
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

        boolean validStationFromName = ValidationCheck.isValidStationName(stationFromName);
        boolean validStationToName = ValidationCheck.isValidStationName(stationToName);
        boolean validDateFromStr = ValidationCheck.isValidDateStr(dateFromStr);
        boolean validDateToStr = ValidationCheck.isValidDateStr(dateToStr);
        boolean validTimeFromStr = ValidationCheck.isValidTimeStr(timeFromStr);
        boolean validTimeToStr = ValidationCheck.isValidTimeStr(timeToStr);

        //Simple train search
        if (validStationFromName && validStationToName && validDateFromStr) {
            List<ScheduleByStation> scheduleList;
            Date dateFrom = DateHelper.convertDate(dateFromStr);
            if (validDateToStr) {
                Date dateTo = DateHelper.convertDate(dateToStr);
                if (validTimeFromStr) {
                    dateFrom = DateHelper.convertDatetime(dateFromStr + " " + timeFromStr);
                }
                if (validTimeToStr) {
                    dateTo = DateHelper.convertDatetime(dateToStr + " " + timeToStr);
                }
                scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom, dateTo);
            } else {
                scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom);
            }

            session.setAttribute("trainSearchingError", false);

            if (scheduleList != null) {
                session.setAttribute("trainList", scheduleList);
            } else {
                session.setAttribute("trainNotFoundError", true);
            }
        }
        getServletContext().getRequestDispatcher("/search_train.jsp").forward(req, resp);
    }
}
