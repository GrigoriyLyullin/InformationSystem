package com.railwaycompany.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class SearchTrainServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(SearchTrainServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/search_train.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String stationFromName = req.getParameter("Station-From-Name");
        String stationToName = req.getParameter("Station-To-Name");
        String dateFrom = req.getParameter("dateFrom") + " " + req.getParameter("timeFrom");
        String dateTo = req.getParameter("dateTo") + " " + req.getParameter("timeTo");

        log.info("stationFromName: " + stationFromName + " stationToName: " + stationToName + " dateFrom: " +
                dateFrom + " dateTo: " + dateTo);

        resp.sendRedirect("/search_train.jsp");
    }
}
