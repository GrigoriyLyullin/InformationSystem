package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.services.interfaces.TicketService;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TicketRestController {

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "ticketRest/getAll", method = RequestMethod.GET)
    public @ResponseBody String getAllTicketsInJSON() {
        JSONArray jsonArray = new JSONArray(ticketService.getAll());
        return jsonArray.toString(4);
    }
}
