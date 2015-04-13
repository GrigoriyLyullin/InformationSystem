package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.ScheduleData;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.business.services.interfaces.StationService;
import com.railwaycompany.utils.DateHelper;
import com.railwaycompany.utils.ValidationHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidDateStr;
import static com.railwaycompany.utils.ValidationHelper.isValidStationName;

@RestController
@RequestMapping("search_train/rest")
public class SearchTrainRestController {


    private static final Logger LOG = Logger.getLogger(ScheduleByStationRestController.class.getName());

    private static final int STEP_SIZE = 3;

    @Autowired
    private StationService stationService;

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ScheduleData> getSchedule(@RequestParam(value = "search-train-from-name") String stationFromName,
                                          @RequestParam(value = "search-train-to-name") String stationToName,
                                          @RequestParam(value = "search-train-date-from") String dateFrom,
                                          @RequestParam(value = "search-train-start-number",
                                                  defaultValue = "0") int startNumber,
                                          HttpServletResponse response) {
        List<ScheduleData> searchTrainSchedule = null;
        if (stationService.exist(stationFromName) && stationService.exist(stationToName)
                && ValidationHelper.isValidDateStr(dateFrom)) {
            Date date = DateHelper.convertDate(dateFrom);
            if (date != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                searchTrainSchedule = scheduleService.
                        getSchedule(stationFromName, stationToName, date, STEP_SIZE, startNumber);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                LOG.warn("Date is valid  but can not be converted");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            if (!stationService.exist(stationFromName)) {
                LOG.warn("Station does not exist. Station name: " + stationFromName);
            } else if (!stationService.exist(stationToName)) {
                LOG.warn("Station does not exist. Station name: " + stationToName);
            }
        }
        return searchTrainSchedule;
    }

    @RequestMapping(value = "maxSize", method = RequestMethod.GET)
    public int getSchedule(@RequestParam(value = "search-train-from-name") String stationFromName,
                           @RequestParam(value = "search-train-to-name") String stationToName,
                           @RequestParam(value = "search-train-date-from") String dateFrom) {
        int maxSize = 0;
        if (isValidStationName(stationFromName) && isValidStationName(stationToName) && isValidDateStr(dateFrom)) {
            if (stationService.exist(stationFromName) && stationService.exist(stationToName)) {
                Date date = DateHelper.convertDate(dateFrom);
                if (date != null) {
                    maxSize = scheduleService.getSchedule(stationFromName, stationToName, date).
                            size() - 1;
                }
            }
        }
        return maxSize;
    }
}
