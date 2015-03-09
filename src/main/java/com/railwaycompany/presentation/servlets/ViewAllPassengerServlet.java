package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.PassengerService;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ViewAllPassengerServlet extends HttpServlet {

    private static final String EMPLOYEE_PAGE = "/WEB-INF/employee_page.jsp";
    private static final String HIDE_ALL_PASSENGERS_PARAM = "hideAllPassengers";
    private static final String TRAIN_ID_PARAM = "trainId";
    private static final String ALL_PASSENGER_LIST_ATTR = "allPassengerList";
    private static final String PASSENGER_NOT_FOUND_ATTR = "passengersNotFound";
    private static final String PASSENGER_NOT_FOUND_TRAIN_ID_ATTR = "passengersNotFoundTrainId";
    private static final String INVALID_TRAIN_ID_ATTR = "invalidTrainIdError";

    private static final Logger LOG = Logger.getLogger(ViewAllPassengerServlet.class.getName());

    private PassengerService passengerService;

    @Override
    public void init() throws ServletException {
        passengerService = ServiceFactorySingleton.getInstance().getPassengerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Boolean.valueOf(req.getParameter(HIDE_ALL_PASSENGERS_PARAM))) {
            req.getSession().removeAttribute(ALL_PASSENGER_LIST_ATTR);
        }
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String trainIdStr = req.getParameter(TRAIN_ID_PARAM);
        List<PassengerData> allPassengersList = null;
        if (ValidationHelper.isValidId(trainIdStr)) {
            int trainId = Integer.valueOf(trainIdStr);
            allPassengersList = passengerService.getAllPassengersByTrainId(trainId);
            if (allPassengersList == null || allPassengersList.isEmpty()) {
                session.setAttribute(PASSENGER_NOT_FOUND_ATTR, true);
                session.setAttribute(PASSENGER_NOT_FOUND_TRAIN_ID_ATTR, trainId);
            }
        } else {
            LOG.info("Invalid trainId: " + trainIdStr);
            session.setAttribute(INVALID_TRAIN_ID_ATTR, true);
        }
        session.setAttribute(ALL_PASSENGER_LIST_ATTR, allPassengersList);
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }
}
