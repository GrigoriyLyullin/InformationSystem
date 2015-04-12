package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.exceptions.TrainWithSuchNumberExistException;
import com.railwaycompany.business.services.interfaces.TrainService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidTrainNumber;
import static com.railwaycompany.utils.ValidationHelper.isValidTrainSeats;

@Controller
@RequestMapping("add_train")
public class AddTrainController {

    private static final Logger LOG = Logger.getLogger(AddTrainController.class);

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
    public String get(HttpServletRequest request, HttpSession session) {
        List<TrainData> allTrainList = null;
        Boolean getAll = Boolean.valueOf(request.getParameter(GET_ALL_TRAIN_PARAM));
        if (getAll) {
            allTrainList = trainService.getAll();
        }
        session.setAttribute(ALL_TRAIN_LIST_ATTR, allTrainList);
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam(value = TRAIN_NUMBER_PARAM) String trainNumberStr,
                       @RequestParam(value = TRAIN_SEATS_PARAM) String trainSeatsStr,
                       @RequestParam(value = ADD_TRAIN_ANYWAY_PARAM) Boolean addTrainAnyway,
                       HttpSession session) {

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
                LOG.warn("Train with such number already exist." + TRAIN_NUMBER_PARAM + ":" + trainNumberStr, e);
            } catch (NumberFormatException e) {
                session.setAttribute(INVALID_INPUT_DATA_ATTR, true);
                StringBuilder msg = new StringBuilder();
                msg.append("Invalid input data: ").append(TRAIN_NUMBER_PARAM).append(" : ").
                        append(trainNumberStr).append(", ").append(TRAIN_SEATS_PARAM).append(" : ").append(trainSeatsStr);
                LOG.warn(msg, e);
            }
        } else {
            session.setAttribute(INVALID_INPUT_DATA_ATTR, true);
            StringBuilder msg = new StringBuilder();
            msg.append("Invalid input data: ").append(TRAIN_NUMBER_PARAM).append(" : ").
                    append(trainNumberStr).append(", ").append(TRAIN_SEATS_PARAM).append(" : ").append(trainSeatsStr);
            LOG.warn(msg);
        }
        return "employee_page";
    }
}
