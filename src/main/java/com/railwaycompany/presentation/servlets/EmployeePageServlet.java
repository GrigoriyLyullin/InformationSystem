package com.railwaycompany.presentation.servlets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("employee_page")
public class EmployeePageServlet extends HttpServlet {

    private static final String EMPLOYEE_PAGE = "/WEB-INF/employee_page.jsp";

    @RequestMapping(method = RequestMethod.GET)
    public String doGet() {
        return "employee_page";
    }
}
