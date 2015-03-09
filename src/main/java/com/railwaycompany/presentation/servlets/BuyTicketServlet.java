package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
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

    private static final String BUY_TICKET_PAGE = "/WEB-INF/buy_ticket.jsp";
    private static final String BUY_TICKET_SUCCESS_PAGE = "/WEB-INF/buy_ticket_success.jsp";
    private static final String BUY_TICKET_ERROR_PAGE = "/WEB-INF/buy_ticket_error.jsp";
    private static final String USER_DATA_ATTR = "userData";
    private static final String HAS_NO_EMPTY_SEATS_ATTR = "buyTicketHasNoEmptySeats";
    private static final String ALREADY_REGISTERED_ATTR = "buyTicketAlreadyRegistered";
    private static final String SALES_STOP_ATTR = "buyTicketSalesStop";
    private static final String INVALID_DATA_ATTR = "buyTicketInvalidData";
    private static final String TICKET_DATA_ATTR = "ticketData";
    private static final String INCORRECT_DATA_ATTR = "buyTicketIncorrectData";
    private static final String TRAIN_NUMBER_PARAM = "trainNumber";
    private static final String STATION_NAME_PARAM = "stationName";
    private static final String DEPARTURE_DATE_PARAM = "departureDate";
    private static final String PASSENGER_NAME_PARAM = "passengerName";
    private static final String PASSENGER_SURNAME_PARAM = "passengerSurname";
    private static final String PASSENGER_BIRTHDATE_PARAM = "passengerBirthdate";

    /**
     * Logger for BuyTicketServlet class.
     */
    private static final Logger LOG = Logger.getLogger(BuyTicketServlet.class.getName());

    private TicketService ticketService;

    @Override
    public void init() throws ServletException {
        ticketService = ServiceFactorySingleton.getInstance().getTicketService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(BUY_TICKET_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String trainNumberParam = req.getParameter(TRAIN_NUMBER_PARAM);
        String departureDateParam = req.getParameter(DEPARTURE_DATE_PARAM);
        String stationNameParam = req.getParameter(STATION_NAME_PARAM);
        String passengerNameParam = req.getParameter(PASSENGER_NAME_PARAM);
        String passengerSurnameParam = req.getParameter(PASSENGER_SURNAME_PARAM);
        String passengerBirthdateParam = req.getParameter(PASSENGER_BIRTHDATE_PARAM);

        boolean checkInput = checkInput(trainNumberParam, departureDateParam, stationNameParam);
        boolean checkPassengerInput = checkPassengerInput(passengerNameParam, passengerSurnameParam,
                passengerBirthdateParam);

        HttpSession session = req.getSession();
        if (checkInput && checkPassengerInput) {
            int trainNumber = Integer.valueOf(trainNumberParam);
            Date departureDate = DateHelper.convertDate(departureDateParam);
            PassengerData passengerData = new PassengerData();
            passengerData.setName(passengerNameParam);
            passengerData.setSurname(passengerSurnameParam);
            passengerData.setBirthdate(DateHelper.convertDate(passengerBirthdateParam));

            boolean error = false;
            TicketData ticketData = null;
            try {
                int userId = ((UserData) session.getAttribute(USER_DATA_ATTR)).getId();
                ticketData = ticketService.buyTicket(userId, trainNumber, departureDate, stationNameParam,
                        passengerData);
            } catch (HasNoEmptySeatsException e) {
                error = true;
                session.setAttribute(HAS_NO_EMPTY_SEATS_ATTR, true);
                LOG.log(Level.WARNING, "This train has no empty seats", e);
            } catch (AlreadyRegisteredException e) {
                error = true;
                session.setAttribute(ALREADY_REGISTERED_ATTR, true);
                LOG.log(Level.WARNING, "Such passenger already registered on this train", e);
            } catch (SalesStopException e) {
                error = true;
                session.setAttribute(SALES_STOP_ATTR, true);
                LOG.log(Level.WARNING, "Ticket sales has been stopped", e);
            } catch (InvalidInputDataException e) {
                error = true;
                session.setAttribute(INVALID_DATA_ATTR, true);
                LOG.log(Level.WARNING, "Invalid input data", e);
            }

            if (error) {
                getServletContext().getRequestDispatcher(BUY_TICKET_ERROR_PAGE).forward(req, resp);
            } else {
                session.setAttribute(TICKET_DATA_ATTR, ticketData);
                getServletContext().getRequestDispatcher(BUY_TICKET_SUCCESS_PAGE).forward(req, resp);
            }

        } else {
            LOG.log(Level.WARNING, "Incorrect input data");
            session.setAttribute(INCORRECT_DATA_ATTR, true);
            getServletContext().getRequestDispatcher(BUY_TICKET_PAGE).forward(req, resp);
        }
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
