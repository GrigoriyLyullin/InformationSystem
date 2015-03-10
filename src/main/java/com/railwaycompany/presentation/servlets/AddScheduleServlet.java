package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.services.exceptions.StationDoesNotExistException;
import com.railwaycompany.business.services.exceptions.SuchScheduleExistException;
import com.railwaycompany.business.services.exceptions.TrainDoesNotExistException;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.utils.DateHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import static com.railwaycompany.utils.ValidationHelper.*;

public class AddScheduleServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(AddScheduleServlet.class.getName());

    private static final String EMPLOYEE_PAGE = "/WEB-INF/employee_page.jsp";

    private ScheduleService scheduleService;

    @Override
    public void init() throws ServletException {
        scheduleService = ServiceFactorySingleton.getInstance().getScheduleService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stationIdStr = req.getParameter("stationId");
        String trainIdStr = req.getParameter("trainId");
        String arrivalDateStr = req.getParameter("arrivalDate");
        String arrivalTimeStr = req.getParameter("arrivalTime");
        String departureDateStr = req.getParameter("departureDate");
        String departureTimeStr = req.getParameter("departureTime");

        HttpSession session = req.getSession();

        if (checkInput(stationIdStr, trainIdStr, arrivalDateStr, arrivalTimeStr, departureDateStr, departureTimeStr)) {

            int stationId = Integer.valueOf(stationIdStr);
            int trainId = Integer.valueOf(trainIdStr);
            Date arrivalDate = DateHelper.convertDatetime(arrivalDateStr, arrivalTimeStr);
            Date departureDate = DateHelper.convertDatetime(departureDateStr, departureTimeStr);

            if (arrivalDate.getTime() < departureDate.getTime()) {
                try {
                    String info = "stationId: " + stationId + " trainId: " + trainId + " " +
                            "arrivalDate: " + arrivalDate + " departureDate: " + departureDate;
                    LOG.info("Try to adding new schedule." + info);
                    scheduleService.addSchedule(stationId, trainId, arrivalDate, departureDate);
                    LOG.info("Schedule has been added." + info);
                    session.setAttribute("AddScheduleSuccess", true);
                } catch (SuchScheduleExistException e) {
                    LOG.warning("Such schedule exist. stationId: " + stationId + " trainId: " + trainId + " " +
                            "arrivalDate: " + arrivalDate + " departureDate: " + departureDate);
                    session.setAttribute("AddScheduleSuchScheduleExistError", true);
                } catch (StationDoesNotExistException e) {
                    LOG.warning("Station with id:" + stationId + " does not exist.");
                    session.setAttribute("AddScheduleStationWithIdDoesNotExistError", true);
                    session.setAttribute("doesNotExistStationId", stationId);
                } catch (TrainDoesNotExistException e) {
                    LOG.warning("Train with id:" + trainId + " does not exist.");
                    session.setAttribute("AddScheduleTrainWithIdDoesNotExistError", true);
                    session.setAttribute("doesNotExistTrainId", trainId);
                }
            } else {
                LOG.warning("Arrival date cannot be greater than departure. arrival date: " + arrivalDate + " " +
                        "departureDate: " + departureDate);
                session.setAttribute("invalidAddScheduleInputDataArrivalGtDepartureError", true);
            }
        } else {
            LOG.warning("Invalid input data. stationId: " + stationIdStr + " trainId: " + trainIdStr + " " +
                    "arrivalDate: " + arrivalDateStr + " arrivalTime: " + arrivalTimeStr + " departureDate: " +
                    departureDateStr + " departureTime: " + departureTimeStr);
            session.setAttribute("invalidAddScheduleInputDataError", true);
        }
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }

    private boolean checkInput(String stationId, String trainId, String arrivalDate, String arrivalTime,
                               String departureDate, String departureTime) {
        return isValidId(stationId) && isValidId(trainId) && isValidDateStr(arrivalDate) && isValidTimeStr(arrivalTime)
                && isValidDateStr(departureDate) && isValidTimeStr(departureTime);
    }
}
