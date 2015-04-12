package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.utils.DateHelper;
import com.railwaycompany.utils.ValidationHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("search_train")
public class SearchTrainController {

    private static final Logger LOG = Logger.getLogger(SearchTrainController.class.getName());

    private static final String STEP_SIZE = "3";

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private StationService stationService;

    @RequestMapping(method = RequestMethod.GET)
    public String getSearchTrain(@RequestParam(value = "station-from-name", required = false) String stationFromName,
                                 @RequestParam(value = "station-to-name", required = false) String stationToName,
                                 @RequestParam(value = "date-from", required = false) String dateFromStr,
                                 HttpServletRequest request, HttpSession session) {

        if (stationFromName != null && stationToName != null && dateFromStr != null) {
            getTrains(stationFromName, stationToName, dateFromStr, Integer.valueOf(STEP_SIZE), 0, Direction.NONE,
                    request, session);
        }
        return "index";
    }

    @RequestMapping(value = "previous", method = RequestMethod.GET)
    public String getPreviousTrain(@RequestParam(value = "station-from-name", required = false) String stationFromName,
                                   @RequestParam(value = "station-to-name", required = false) String stationToName,
                                   @RequestParam(value = "date-from", required = false) String dateFromStr,
                                   @RequestParam(value = "schedule-by-station-step-size",
                                           defaultValue = STEP_SIZE) int stepSize,
                                   @RequestParam(value = "schedule-by-station-start-number",
                                           defaultValue = "0") int startNumber,
                                   HttpServletRequest request, HttpSession session) {
        getTrains(stationFromName, stationToName, dateFromStr, stepSize, startNumber, Direction.PREVIOUS,
                request, session);
        return "index";
    }

    @RequestMapping(value = "next", method = RequestMethod.GET)
    public String getNextTrain(@RequestParam(value = "station-from-name", required = false) String stationFromName,
                               @RequestParam(value = "station-to-name", required = false) String stationToName,
                               @RequestParam(value = "date-from", required = false) String dateFromStr,
                               @RequestParam(value = "schedule-by-station-step-size",
                                       defaultValue = STEP_SIZE) int stepSize,
                               @RequestParam(value = "schedule-by-station-start-number",
                                       defaultValue = "0") int startNumber,
                               HttpServletRequest request, HttpSession session) {
        getTrains(stationFromName, stationToName, dateFromStr, stepSize, startNumber, Direction.NEXT,
                request, session);
        return "index";
    }

    private void getTrains(String stationFromName, String stationToName, String dateFromStr,
                           int stepSize, int startNumber, Direction direction,
                           HttpServletRequest request, HttpSession session) {
        session.setAttribute("stationFromName", stationFromName);
        session.setAttribute("stationToName", stationToName);
        session.setAttribute("dateFrom", dateFromStr);

        boolean validStationFromName = ValidationHelper.isValidStationName(stationFromName);
        boolean validStationToName = ValidationHelper.isValidStationName(stationToName);
        boolean validDateFromStr = ValidationHelper.isValidDateStr(dateFromStr);

        if (validStationFromName && validStationToName && validDateFromStr) {
            if (stationService.exist(stationFromName) && stationService.exist(stationToName)) {
                List<ScheduleData> scheduleList;
                Date dateFrom = DateHelper.convertDate(dateFromStr);

                int maxSize;
                Object maxSizeAttr = request.getAttribute("searchTrainMaxSize");
                if (maxSizeAttr == null) {
                    maxSize = scheduleService.getSchedule(stationFromName, stationToName, dateFrom).size() - 1;
                    request.setAttribute("searchTrainMaxSize", maxSize);
                } else {
                    maxSize = (int) maxSizeAttr;
                }
                if (direction == Direction.PREVIOUS) {
                    startNumber = (startNumber >= stepSize) ? startNumber - stepSize : 0;
                } else if (direction == Direction.NEXT) {
                    startNumber = (startNumber < maxSize) ? startNumber + stepSize : maxSize;
                }

                scheduleList = scheduleService.getSchedule(stationFromName, stationToName, dateFrom,
                        stepSize, startNumber);
                if (scheduleList != null && !scheduleList.isEmpty()) {
                    request.setAttribute("searchTrainStartNumber", startNumber);
                    request.setAttribute("trainList", scheduleList);
                } else {
                    request.setAttribute("trainNotFoundError", true);
                }

            } else {
                LOG.warn("Stations with such names does not exist. stationFrom: " + stationFromName +
                        " stationTo: " + stationToName);
                if (!stationService.exist(stationFromName)) {
                    request.setAttribute("stationDoesNotExist", stationFromName);
                } else if (!stationService.exist(stationToName)) {
                    request.setAttribute("stationDoesNotExist", stationToName);
                }
                request.setAttribute("stationWithSuchNameDoesNotExist", true);
            }
        } else {
            LOG.warn("Invalid input data. stationFrom: " + stationFromName + " stationTo: " +
                    stationToName + " dateFrom: " + dateFromStr);
            request.setAttribute("trainSearchingInvalidInput", true);
        }
    }
}
