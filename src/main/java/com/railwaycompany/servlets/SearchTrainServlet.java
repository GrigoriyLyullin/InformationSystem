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
        resp.sendRedirect("/search_train.jsp");
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

        session.setAttribute("trainNotFound", true);
        session.setAttribute("trainList", null);

        boolean validStationFromName = ValidationCheck.isValidStationName(stationFromName);
        boolean validStationToName = ValidationCheck.isValidStationName(stationToName);
        boolean validDateFromStr = ValidationCheck.isValidDateStr(dateFromStr);
        boolean validDateToStr = ValidationCheck.isValidDateStr(dateToStr);
        boolean validTimeFromStr = ValidationCheck.isValidTimeStr(timeFromStr);
        boolean validTimeToStr = ValidationCheck.isValidTimeStr(timeToStr);

        if (validStationFromName && validStationToName) {
            if (!stationFromName.equals(stationToName)) {

                if (validDateFromStr && validDateToStr) {
                    Date dateFrom;
                    Date dateTo;
                    if (validTimeFromStr && validTimeToStr) {
                        dateFrom = DateHelper.convertDatetime(dateFromStr + " " + timeFromStr);
                        dateTo = DateHelper.convertDatetime(dateToStr + " " + timeToStr);
                    } else {
                        dateFrom = DateHelper.convertDate(dateFromStr);
                        dateTo = DateHelper.convertDate(dateToStr);
                    }
                    List<ScheduleByStation> scheduleList;
                    if (dateFrom != null && dateTo != null) {
                        scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom, dateTo);

                        session.setAttribute("trainSearchingError", false);
                        session.setAttribute("trainNotFound", false);
                        session.setAttribute("trainList", scheduleList);
                    }
                }
            } else {
                session.setAttribute("trainSearchingError", true);
            }
        }
        getServletContext().getRequestDispatcher("/search_train.jsp").forward(req, resp);
    }
}
