package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidTrainNumber;
import static com.railwaycompany.utils.ValidationHelper.isValidTrainSeats;

@Controller
@RequestMapping("add_train")
public class AddTrainController {

    private static final String GET_ALL_TRAIN_PARAM = "getAllTrain";
    private static final String TRAIN_NUMBER_PARAM = "trainNumber";
    private static final String TRAIN_SEATS_PARAM = "trainSeats";
    private static final String ADD_TRAIN_ANYWAY_PARAM = "addTrainAnyway";
    private static final String ALL_TRAIN_LIST_ATTR = "allTrainList";
    private static final String INVALID_INPUT_DATA_ATTR = "invalidInputDataError";
    private static final String EXIST_TRAIN_WARNING_ATTR = "existTrainWarning";
    private static final String EXIST_TRAIN_NUMBER_ATTR = "existTrainNumber";
    private static final String EXIST_TRAIN_SEATS_ATTR = "existTrainSeats";

    @Autowired
    private TrainService trainService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest req) {
        List<TrainData> allTrainList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter(GET_ALL_TRAIN_PARAM));
        if (getAll) {
            allTrainList = trainService.getAll();
        }
        req.getSession().setAttribute(ALL_TRAIN_LIST_ATTR, allTrainList);
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(HttpServletRequest req) {

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
        return "employee_page";
    }
}
