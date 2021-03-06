package com.railwaycompany.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.railwaycompany.business.services.interfaces.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("stations")
public class StationRestController {

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getStationNames(@RequestParam(value = "startWith", required = false) String startWithStr,
                                  HttpServletResponse response) {
        List<String> stationNameList = stationService.getAllStationNames(startWithStr);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(stationNameList);
    }
}
