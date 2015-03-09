package com.railwaycompany.presentation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IndexPageServlet extends HttpServlet {

    private static final String INDEX_PAGE = "/WEB-INF/index.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }
}
