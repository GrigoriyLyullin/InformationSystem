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

    private static final Logger LOG = Logger.getLogger(ViewAllPassengerServlet.class.getName());

    private PassengerService passengerService;

    @Override
    public void init() throws ServletException {
        passengerService = ServiceFactorySingleton.getInstance().getPassengerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Boolean.valueOf(req.getParameter("hideAllPassengers"))) {
            req.getSession().removeAttribute("allPassengerList");
        }
        getServletContext().getRequestDispatcher("/employee_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String trainIdStr = req.getParameter("trainId");
        List<PassengerData> allPassengersList = null;
        if (ValidationHelper.isValidId(trainIdStr)) {
            int trainId = Integer.valueOf(trainIdStr);
            allPassengersList = passengerService.getAllPassengersByTrainId(trainId);
            if (allPassengersList == null || allPassengersList.isEmpty()) {
                session.setAttribute("passengersNotFound", true);
                session.setAttribute("passengersNotFoundTrainId", trainId);
            }
        } else {
            LOG.info("Invalid trainId: " + trainIdStr);
            session.setAttribute("invalidTrainIdError", true);
        }
        session.setAttribute("allPassengerList", allPassengersList);
        getServletContext().getRequestDispatcher("/employee_page.jsp").forward(req, resp);
    }
}
