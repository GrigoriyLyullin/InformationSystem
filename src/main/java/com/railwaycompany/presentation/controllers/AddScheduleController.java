package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.services.exceptions.StationDoesNotExistException;
import com.railwaycompany.business.services.exceptions.SuchScheduleExistException;
import com.railwaycompany.business.services.exceptions.TrainDoesNotExistException;
import com.railwaycompany.business.services.interfaces.ScheduleService;
import com.railwaycompany.utils.DateHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

import static com.railwaycompany.utils.ValidationHelper.*;

@Controller
@RequestMapping("add_schedule")
public class AddScheduleController {

    private static final Logger LOG = Logger.getLogger(AddScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "employee_page";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String post(@RequestParam(value = "stationId") String stationIdStr,
                          @RequestParam(value = "trainId") String trainIdStr,
                          @RequestParam(value = "arrivalDate") String arrivalDateStr,
                          @RequestParam(value = "arrivalTime") String arrivalTimeStr,
                          @RequestParam(value = "departureDate") String departureDateStr,
                          @RequestParam(value = "departureTime") String departureTimeStr,
                          HttpSession session) {

        if (isValidId(stationIdStr) && isValidId(trainIdStr) && isValidDateStr(arrivalDateStr)
                && isValidTimeStr(arrivalTimeStr) && isValidDateStr(departureDateStr)
                && isValidTimeStr(departureTimeStr)) {

            int stationId = Integer.valueOf(stationIdStr);
            int trainId = Integer.valueOf(trainIdStr);
            Date arrivalDate = DateHelper.convertDatetime(arrivalDateStr, arrivalTimeStr);
            Date departureDate = DateHelper.convertDatetime(departureDateStr, departureTimeStr);

            if (arrivalDate.getTime() < departureDate.getTime()) {
                try {
                    String info = "stationId: " + stationId + " trainId: " + trainId + " " +
                            "arrivalDate: " + arrivalDate + " departureDate: " + departureDate;
                    LOG.info("Try to adding new schedule." + info);
                    scheduleService.addSchedule(stationId, trainId, arrivalDate, departureDate);
                    LOG.info("Schedule has been added." + info);
                    session.setAttribute("AddScheduleSuccess", true);
                } catch (SuchScheduleExistException e) {
                    LOG.warn("Such schedule exist. stationId: " + stationId + " trainId: " + trainId + " " +
                            "arrivalDate: " + arrivalDate + " departureDate: " + departureDate);
                    session.setAttribute("AddScheduleSuchScheduleExistError", true);
                } catch (StationDoesNotExistException e) {
                    LOG.warn("Station with id:" + stationId + " does not exist.");
                    session.setAttribute("AddScheduleStationWithIdDoesNotExistError", true);
                    session.setAttribute("doesNotExistStationId", stationId);
                } catch (TrainDoesNotExistException e) {
                    LOG.warn("Train with id:" + trainId + " does not exist.");
                    session.setAttribute("AddScheduleTrainWithIdDoesNotExistError", true);
                    session.setAttribute("doesNotExistTrainId", trainId);
                }
            } else {
                LOG.warn("Arrival date cannot be greater than departure. arrival date: " + arrivalDate + " " +
                        "departureDate: " + departureDate);
                session.setAttribute("invalidAddScheduleInputDataArrivalGtDepartureError", true);
            }
        } else {
            LOG.warn("Invalid input data. stationId: " + stationIdStr + " trainId: " + trainIdStr + " " +
                    "arrivalDate: " + arrivalDateStr + " arrivalTime: " + arrivalTimeStr + " departureDate: " +
                    departureDateStr + " departureTime: " + departureTimeStr);
            session.setAttribute("invalidAddScheduleInputDataError", true);
        }
        return "employee_page";
    }
}
