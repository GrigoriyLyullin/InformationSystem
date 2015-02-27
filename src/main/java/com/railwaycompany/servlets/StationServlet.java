package com.railwaycompany.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/schedule_by_station.html");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationName = req.getParameter("Station-name");

        if (stationName != null && stationName.isEmpty()) {

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("stationName " + stationName);

        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/unexpected_error.html");
            dispatcher.forward(req, resp);
        }
    }
}
