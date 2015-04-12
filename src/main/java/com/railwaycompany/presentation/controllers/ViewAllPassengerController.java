package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.services.interfaces.PassengerService;
import com.railwaycompany.utils.ValidationHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("view_all_passengers")
public class ViewAllPassengerController {

    private static final Logger LOG = Logger.getLogger(ViewAllPassengerController.class.getName());

    private static final String HIDE_ALL_PASSENGERS_PARAM = "hideAllPassengers";
    private static final String TRAIN_ID_PARAM = "trainId";
    private static final String ALL_PASSENGER_LIST_ATTR = "allPassengerList";
    private static final String PASSENGER_NOT_FOUND_ATTR = "passengersNotFound";
    private static final String PASSENGER_NOT_FOUND_TRAIN_ID_ATTR = "passengersNotFoundTrainId";
    private static final String INVALID_TRAIN_ID_ATTR = "invalidTrainIdError";

    @Autowired
    private PassengerService passengerService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam(value = HIDE_ALL_PASSENGERS_PARAM) boolean hideAll,
                      HttpSession session) {
        if (hideAll) {
            session.removeAttribute(ALL_PASSENGER_LIST_ATTR);
        }
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@RequestParam(value = TRAIN_ID_PARAM) String trainIdStr,
                         HttpSession session) {
        List<PassengerData> allPassengersList = null;
        if (ValidationHelper.isValidId(trainIdStr)) {
            int trainId = Integer.valueOf(trainIdStr);
            allPassengersList = passengerService.getAllPassengersByTrainId(trainId);
            if (allPassengersList == null || allPassengersList.isEmpty()) {
                session.setAttribute(PASSENGER_NOT_FOUND_ATTR, true);
                session.setAttribute(PASSENGER_NOT_FOUND_TRAIN_ID_ATTR, trainId);
            }
        } else {
            LOG.warn("Invalid trainId: " + trainIdStr);
            session.setAttribute(INVALID_TRAIN_ID_ATTR, true);
        }
        session.setAttribute(ALL_PASSENGER_LIST_ATTR, allPassengersList);
        return "employee_page";
    }
}
