package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.TrainData;
import com.railwaycompany.business.services.interfaces.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("view_all_trains")
public class ViewAllTrainsController {

    private static final String VIEW_ALL_TRAIN_PARAM = "viewAllTrains";
    private static final String VIEW_ALL_TRAIN_LIST_ATTR = "viewAllTrainsList";

    @Autowired
    private TrainService trainService;

    @RequestMapping(method = RequestMethod.GET)
    public String doGet(HttpServletRequest req) {
        List<TrainData> viewAllTrainsList = null;
        Boolean getAll = Boolean.valueOf(req.getParameter(VIEW_ALL_TRAIN_PARAM));
        if (getAll) {
            viewAllTrainsList = trainService.getAll();
        }
        req.getSession().setAttribute(VIEW_ALL_TRAIN_LIST_ATTR, viewAllTrainsList);
        return "employee_page";
    }
}
