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

    private static final String EMPLOYEE_PAGE = "/WEB-INF/employee_page.jsp";
    private static final String GET_ALL_TRAIN_PARAM = "getAllTrain";
    private static final String TRAIN_NUMBER_PARAM = "trainNumber";
    private static final String TRAIN_SEATS_PARAM = "trainSeats";
    private static final String ADD_TRAIN_ANYWAY_PARAM = "addTrainAnyway";
    private static final String ALL_TRAIN_LIST_ATTR = "allTrainList";
    private static final String INVALID_INPUT_DATA_ATTR = "invalidInputDataError";
    private static final String EXIST_TRAIN_WARNING_ATTR = "existTrainWarning";
    private static final String EXIST_TRAIN_NUMBER_ATTR = "existTrainNumber";
    private static final String EXIST_TRAIN_SEATS_ATTR = "existTrainSeats";

    private TrainService trainService;

    @Override
    public void init() throws ServletException {
        trainService = ServiceFactorySingleton.getInstance().getTrainService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TrainData> allTrainList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter(GET_ALL_TRAIN_PARAM));
        if (getAll) {
            allTrainList = trainService.getAll();
        }
        req.getSession().setAttribute(ALL_TRAIN_LIST_ATTR, allTrainList);
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String trainNumberStr = req.getParameter(TRAIN_NUMBER_PARAM);
        String trainSeatsStr = req.getParameter(TRAIN_SEATS_PARAM);
        boolean addTrainAnyway = Boolean.valueOf(req.getParameter(ADD_TRAIN_ANYWAY_PARAM));
        boolean allTrainListExist = (session.getAttribute(ALL_TRAIN_LIST_ATTR) != null);

        if (isValidTrainNumber(trainNumberStr) && isValidTrainSeats(trainSeatsStr)) {
            try {
                int trainNumber = Integer.valueOf(trainNumberStr);
                int trainSeats = Integer.valueOf(trainSeatsStr);
                trainService.addTrain(trainNumber, trainSeats, addTrainAnyway);
                if (allTrainListExist) {
                    session.setAttribute(ALL_TRAIN_LIST_ATTR, trainService.getAll());
                }
            } catch (TrainWithSuchNumberExistException e) {
                session.setAttribute(EXIST_TRAIN_WARNING_ATTR, true);
                session.setAttribute(EXIST_TRAIN_NUMBER_ATTR, trainNumberStr);
                session.setAttribute(EXIST_TRAIN_SEATS_ATTR, trainSeatsStr);
            } catch (NumberFormatException e) {
                session.setAttribute(INVALID_INPUT_DATA_ATTR, true);
            }
        } else {
            session.setAttribute(INVALID_INPUT_DATA_ATTR, true);
        }
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }
}
