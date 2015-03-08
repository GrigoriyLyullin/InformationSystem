package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.utils.DateHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.railwaycompany.utils.ValidationHelper.*;

public class BuyTicketServlet extends HttpServlet {

    /**
     * Logger for BuyTicketServlet class.
     */
    private static Logger log = Logger.getLogger(BuyTicketServlet.class.getName());

    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
//        trainService = ServiceFactorySingleton.getInstance().getTrainService();
        ticketService = ServiceFactorySingleton.getInstance().getTicketService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/buy_ticket.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        log.info("start buying ticket...");

        HttpSession session = req.getSession();
        UserData userData = (UserData) session.getAttribute("userData");

        int userId = userData.getId();

        String trainNumberParam = req.getParameter("trainNumber");
        String departureDateParam = req.getParameter("departureDate");
        String stationNameParam = req.getParameter("stationName");

        String passengerNameParam = req.getParameter("passengerName");
        String passengerSurnameParam = req.getParameter("passengerSurname");
        String passengerBirthdateParam = req.getParameter("passengerBirthdate");

        log.info("trainNumberParam: " + trainNumberParam + " departureDateParam: " + departureDateParam + " " +
                "stationNameParam: " + stationNameParam);
        log.info("passengerNameParam: " + passengerNameParam + " passengerSurnameParam: " + passengerSurnameParam + "" +
                " passengerBirthdateParam: " + passengerBirthdateParam);

        boolean checkInput = checkInput(trainNumberParam, departureDateParam, stationNameParam);
        boolean checkPassengerInput = checkPassengerInput(passengerNameParam, passengerSurnameParam,
                passengerBirthdateParam);

        if (checkInput && checkPassengerInput) {

            int trainNumber = Integer.valueOf(trainNumberParam);
            Date departureDate = DateHelper.convertDate(departureDateParam);
            PassengerData passengerData = new PassengerData();
            passengerData.setName(passengerNameParam);
            passengerData.setSurname(passengerSurnameParam);
            passengerData.setBirthdate(DateHelper.convertDate(passengerBirthdateParam));
            try {
                ticketService.buyTicket(userId, trainNumber, departureDate, stationNameParam, passengerData);
            } catch (HasNoEmptySeatsException | AlreadyRegisteredException | SalesStopException | InvalidInputDataException e) {
                log.log(Level.WARNING, "", e);
            }
        }

        log.info("stop buying ticket");
    }

    private boolean checkPassengerInput(String passengerNameParam, String passengerSurnameParam,
                                        String passengerBirthdateParam) {
        return isValidPassengerNameOrSurname(passengerNameParam) && isValidPassengerNameOrSurname
                (passengerSurnameParam) && isValidDateStr(passengerBirthdateParam);

    }

    private boolean checkInput(String trainNumberParam, String departureDateParam, String stationNameParam) {
        return isValidTrainNumber(trainNumberParam) && isValidDateStr(departureDateParam) && isValidStationName
                (stationNameParam);
    }
}
