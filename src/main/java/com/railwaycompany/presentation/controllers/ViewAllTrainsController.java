package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("view_all_trains")
public class ViewAllTrainsController {

    private static final String VIEW_ALL_TRAIN_PARAM = "viewAllTrains";
    private static final String VIEW_ALL_TRAIN_LIST_ATTR = "viewAllTrainsList";

    @Autowired
    private TrainService trainService;

    @RequestMapping(method = RequestMethod.GET)
    public String get(@RequestParam(value = VIEW_ALL_TRAIN_PARAM) boolean getAll, HttpSession session) {
        List<TrainData> viewAllTrainsList = null;
        if (getAll) {
            viewAllTrainsList = trainService.getAll();
        }
        session.setAttribute(VIEW_ALL_TRAIN_LIST_ATTR, viewAllTrainsList);
        return "employee_page";
    }
}
