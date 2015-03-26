package com.railwaycompany.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("employee_page")
public class EmployeePageController {

    @RequestMapping(method = RequestMethod.GET)
    public String doGet() {
        return "employee_page";
    }
}
