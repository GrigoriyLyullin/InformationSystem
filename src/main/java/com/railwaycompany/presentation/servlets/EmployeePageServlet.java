package com.railwaycompany.presentation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeePageServlet extends HttpServlet {

    private static final String EMPLOYEE_PAGE = "/WEB-INF/employee_page.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(EMPLOYEE_PAGE).forward(req, resp);
    }
}
