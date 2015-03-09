package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.TrainService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidTrainNumber;
import static com.railwaycompany.utils.ValidationHelper.isValidTrainSeats;

public class AddTrainServlet extends HttpServlet {

    private TrainService trainService;

    @Override
    public void init() throws ServletException {
        trainService = ServiceFactorySingleton.getInstance().getTrainService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TrainData> allTrainList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter("getAllTrain"));
        if (getAll) {
            allTrainList = trainService.getAll();
        }
        req.getSession().setAttribute("allTrainList", allTrainList);
        getServletContext().getRequestDispatcher("/WEB-INF/employee_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String trainNumberStr = req.getParameter("trainNumber");
        String trainSeatsStr = req.getParameter("trainSeats");
        boolean addTrainAnyway = Boolean.valueOf(req.getParameter("addTrainAnyway"));
        boolean allTrainListExist = (session.getAttribute("allTrainList") != null);

        if (isValidTrainNumber(trainNumberStr) && isValidTrainSeats(trainSeatsStr)) {
            try {
                int trainNumber = Integer.valueOf(trainNumberStr);
                int trainSeats = Integer.valueOf(trainSeatsStr);
                trainService.addTrain(trainNumber, trainSeats, addTrainAnyway);
                if (allTrainListExist) {
                    session.setAttribute("allTrainList", trainService.getAll());
                }
            } catch (TrainWithSuchNumberExistException e) {
                session.setAttribute("existTrainWarning", true);
                session.setAttribute("existTrainNumber", trainNumberStr);
                session.setAttribute("existTrainSeats", trainSeatsStr);
            } catch (NumberFormatException e) {
                session.setAttribute("invalidInputDataError", true);
            }
        } else {
            session.setAttribute("invalidInputDataError", true);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/employee_page.jsp").forward(req, resp);
    }
}
