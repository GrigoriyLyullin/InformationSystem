package com.railwaycompany.presentation.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.persistence.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.railwaycompany.utils.ValidationHelper.isValidDateStr;

@RestController
@RequestMapping("tickets")
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(method = RequestMethod.GET)
    public String getTickets(@RequestParam(value = "dateFrom", required = false) String dateFrom,
                                   @RequestParam(value = "timeFrom", defaultValue = "00:00") String timeFrom,
                                   @RequestParam(value = "dateTo", required = false) String dateTo,
                                   @RequestParam(value = "timeTo", defaultValue = "00:00") String timeTo,
                                   HttpServletResponse response) {
        List<Ticket> ticketList = null;
        if (isValidDateStr(dateFrom) && isValidDateStr(dateTo)) {
            response.setStatus(HttpServletResponse.SC_OK);
            ticketList = ticketService.getTickets(dateFrom, timeFrom, dateTo, timeTo);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").setPrettyPrinting().create();
        return gson.toJson(ticketList);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void postTicket(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTicket(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void putTicket(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
