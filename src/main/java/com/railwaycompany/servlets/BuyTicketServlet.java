package com.railwaycompany.servlets;

import com.railwaycompany.services.abstractServices.TicketService;
import com.railwaycompany.services.abstractServices.TrainService;
import com.railwaycompany.services.servicesImpl.ServiceFactorySingleton;
import com.railwaycompany.utils.DateHelper;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class BuyTicketServlet extends HttpServlet {

    /**
     * Logger for BuyTicketServlet class.
     */
    private static Logger log = Logger.getLogger(BuyTicketServlet.class.getName());

    private TrainService trainService;
    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
        trainService = ServiceFactorySingleton.getInstance().getTrainService();
        ticketService = ServiceFactorySingleton.getInstance().getTicketService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/buy_ticket.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        Integer userId = (Integer) session.getAttribute("userId");
        String trainNumberStr = req.getParameter("Train-Number");
        String stationFromName = req.getParameter("Station-From-Name");

        log.info("User id: " + userId + " trainNumberStr: " + trainNumberStr + " stationFromName: " + stationFromName);

        if (userId != null && ValidationHelper.isValidTrainNumber(trainNumberStr) && ValidationHelper
                .isValidStationName(stationFromName)) {
            Integer trainNumber = Integer.valueOf(trainNumberStr);
            Integer trainId = trainService.getTrainId(trainNumber);

            if (trainId != null) {
                if (trainService.hasEmptySeats(trainId)) {
                    log.info("Has Empty Seats");
                    if (!trainService.isRegistered(trainId, userId)) {
                        log.info("User: " + userId + " is not registered on " + " train: " + trainId);
                        Date departureDate = trainService.getDepartureDate(stationFromName, trainId);
                        log.info("departureDate: " + departureDate);
                        if (DateHelper.isEnoughTime(departureDate, DateHelper.MILLIS_IN_TEN_MINUTES)) {
                            log.info("buying ticket...");
                            ticketService.buyTicket(userId, trainId);
                        } else {
                            log.info("Train " + trainId + " is already off");
                        }
                    }
                }
            }


        }

        getServletContext().getRequestDispatcher("/buy_ticket.jsp").forward(req, resp);
    }
}
