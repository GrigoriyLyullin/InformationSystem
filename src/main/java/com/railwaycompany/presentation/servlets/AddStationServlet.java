package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.StationData;
import com.railwaycompany.business.services.exceptions.StationWithSuchNameExistException;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AddStationServlet extends HttpServlet {

    private StationService stationService;

    @Override
    public void init() throws ServletException {
        stationService = ServiceFactorySingleton.getInstance().getStationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StationData> allStationList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter("getAllStation"));
        if (getAll) {
            allStationList = stationService.getAll();
        }
        req.getSession().setAttribute("allStationList", allStationList);
        getServletContext().getRequestDispatcher("/employee_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String stationName = req.getParameter("stationName");
        boolean allStationListExist = (session.getAttribute("allStationList") != null);

        if (ValidationHelper.isValidStationName(stationName)) {
            try {
                stationService.addStation(stationName);
                if (allStationListExist) {
                    session.setAttribute("allStationList", stationService.getAll());
                }
            } catch (StationWithSuchNameExistException e) {
                session.setAttribute("existStationError", true);
                session.setAttribute("existStationName", stationName);
            }
        } else {
            session.setAttribute("invalidStationNameError", true);
        }
        getServletContext().getRequestDispatcher("/employee_page.jsp").forward(req, resp);
    }
}
