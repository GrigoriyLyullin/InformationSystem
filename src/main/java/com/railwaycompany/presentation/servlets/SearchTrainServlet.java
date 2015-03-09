package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.ScheduleService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchTrainServlet extends HttpServlet {

    private static final String INDEX_PAGE = "/WEB-INF/index.jsp";
    private static final String EXTENDED_FORM_PARAM = "extendedForm";
    private static final String STATION_FORM_NAME_PARAM = "Station-From-Name";
    private static final String STATION_TO_NAME_PARAM = "Station-To-Name";
    private static final String DATE_FROM_PARAM = "dateFrom";
    private static final String DATE_TO_PARAM = "dateTo";
    private static final String TIME_FROM_PARAM = "timeFrom";
    private static final String TIME_TO_PARAM = "timeTo";
    private static final String EXTENDED_FORM_ATTR = "extendedForm";
    private static final String STATION_FROM_NAME_ATTR = "stationFromName";
    private static final String STATION_TO_NAME_ATTR = "stationToName";
    private static final String DATE_FROM_ATTR = "dateFrom";
    private static final String DATE_TO_ATTR = "dateTo";
    private static final String TRAIN_SEARCHING_ERROR_ATTR = "trainSearchingError";
    private static final String TRAIN_LIST_ATTR = "trainList";
    private static final String TRAIN_NOT_FOUND_ATTR = "trainNotFoundError";

    private static final Logger LOG = Logger.getLogger(SearchTrainServlet.class.getName());

    private ScheduleService scheduleService;

    @Override
    public void init() throws ServletException {
        scheduleService = ServiceFactorySingleton.getInstance().getScheduleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute(EXTENDED_FORM_ATTR, Boolean.valueOf(req.getParameter(EXTENDED_FORM_PARAM)));
        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stationFromName = req.getParameter(STATION_FORM_NAME_PARAM);
        String stationToName = req.getParameter(STATION_TO_NAME_PARAM);
        String dateFromStr = req.getParameter(DATE_FROM_PARAM);
        String dateToStr = req.getParameter(DATE_TO_PARAM);
        String timeFromStr = req.getParameter(TIME_FROM_PARAM);
        String timeToStr = req.getParameter(TIME_TO_PARAM);

        HttpSession session = req.getSession();
        session.setAttribute(STATION_FROM_NAME_ATTR, stationFromName);
        session.setAttribute(STATION_TO_NAME_ATTR, stationToName);
        session.setAttribute(DATE_FROM_ATTR, dateFromStr);
        session.setAttribute(DATE_TO_ATTR, dateToStr);

        boolean validStationFromName = ValidationHelper.isValidStationName(stationFromName);
        boolean validStationToName = ValidationHelper.isValidStationName(stationToName);
        boolean validDateFromStr = ValidationHelper.isValidDateStr(dateFromStr);
        boolean validDateToStr = ValidationHelper.isValidDateStr(dateToStr);
        boolean validTimeFromStr = ValidationHelper.isValidTimeStr(timeFromStr);
        boolean validTimeToStr = ValidationHelper.isValidTimeStr(timeToStr);

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
                scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom, dateTo);
            } else {
                scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom);
            }
            session.setAttribute(TRAIN_SEARCHING_ERROR_ATTR, false);
            if (scheduleList != null) {
                session.setAttribute(TRAIN_LIST_ATTR, scheduleList);
            } else {
                session.setAttribute(TRAIN_NOT_FOUND_ATTR, true);
            }
        } else {
            LOG.log(Level.WARNING, "Incorrect minimum data. stationFrom: " + stationFromName + " stationTo: " +
                    stationToName + " dateFrom: " + dateFromStr);
        }
        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }
}
