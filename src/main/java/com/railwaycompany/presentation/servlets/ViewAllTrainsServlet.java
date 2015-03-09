package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ViewAllTrainsServlet extends HttpServlet {

    private TrainService trainService;

    @Override
    public void init() throws ServletException {
        trainService = ServiceFactorySingleton.getInstance().getTrainService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TrainData> viewAllTrainsList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter("viewAllTrains"));
        if (getAll) {
            viewAllTrainsList = trainService.getAll();
        }
        req.getSession().setAttribute("viewAllTrainsList", viewAllTrainsList);
        getServletContext().getRequestDispatcher("/WEB-INF/employee_page.jsp").forward(req, resp);
    }
}
