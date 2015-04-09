package com.railwaycompany.presentation.controllers;

import com.railwaycompany.business.dto.PassengerData;
import com.railwaycompany.business.dto.TicketData;
import com.railwaycompany.business.services.exceptions.AlreadyRegisteredException;
import com.railwaycompany.business.services.exceptions.HasNoEmptySeatsException;
import com.railwaycompany.business.services.exceptions.InvalidInputDataException;
import com.railwaycompany.business.services.exceptions.SalesStopException;
import com.railwaycompany.business.services.interfaces.TicketService;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.logging.Logger;

import static com.railwaycompany.utils.ValidationHelper.*;

@Controller
@RequestMapping("buy_ticket")
public class BuyTicketController {

//    private static final String USER_DATA_ATTR = "userData";
//    private static final String HAS_NO_EMPTY_SEATS_ATTR = "buyTicketHasNoEmptySeats";
//    private static final String ALREADY_REGISTERED_ATTR = "buyTicketAlreadyRegistered";
//    private static final String SALES_STOP_ATTR = "buyTicketSalesStop";
//    private static final String INVALID_DATA_ATTR = "buyTicketInvalidData";
//    private static final String TICKET_DATA_ATTR = "ticketData";
//    private static final String INCORRECT_DATA_ATTR = "buyTicketIncorrectData";
//    private static final String TRAIN_NUMBER_PARAM = "trainNumber";
//    private static final String STATION_NAME_PARAM = "stationName";
//    private static final String DEPARTURE_DATE_PARAM = "departureDate";
//    private static final String PASSENGER_NAME_PARAM = "passengerName";
//    private static final String PASSENGER_SURNAME_PARAM = "passengerSurname";
//    private static final String PASSENGER_BIRTHDATE_PARAM = "passengerBirthdate";
//    private static final String NOT_ADULT_PASSENGER_ATTR = "passengerIsNotAdult";
//
//    private final static int PASSENGER_MIN_AGE = 14;

    /**
     * Logger for BuyTicketServlet class.
     */
    private static final Logger LOG = Logger.getLogger(BuyTicketController.class.getName());

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(value = "step", required = false, defaultValue = "1") int step) {
        ModelAndView modelAndView = new ModelAndView("buy_ticket");
        modelAndView.addObject("step", (step > 3 || step < 1) ? 1 : step);
        return modelAndView;
    }

    @RequestMapping(value = "step_1", method = RequestMethod.POST)
    public ModelAndView postStep1(@RequestParam(value = "trainNumber") String trainNumber,
                                  @RequestParam(value = "dispatchStation") String dispatchStation,
                                  @RequestParam(value = "departureDate") String departureDate,
                                  @RequestParam(value = "departureTime") String departureTime,
                                  HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("buy_ticket");

        session.setAttribute("trainNumber", trainNumber);
        session.setAttribute("dispatchStation", dispatchStation);
        session.setAttribute("departureDate", departureDate);
        session.setAttribute("departureTime", departureTime);

        if (isValidTrainNumber(trainNumber) && isValidStationName(dispatchStation)
                && isValidDateStr(departureDate) && isValidTimeStr(departureTime)) {

            Integer trainNumberInt = Integer.valueOf(trainNumber);
            Date date = DateHelper.convertDatetime(departureDate, departureTime);

            try {
                boolean hasEmptySeats = ticketService.hasEmptySeats(trainNumberInt, dispatchStation, date);
                boolean hasEnoughTime = ticketService.hasEnoughTimeBeforeDeparture(trainNumberInt,
                        dispatchStation, date);

                if (hasEmptySeats) {
                    if (hasEnoughTime) {
                        modelAndView.addObject("step", 2);
                    } else {
                        modelAndView.addObject("step", 1);
                        modelAndView.addObject("hasNotEnoughTime", true);
                    }
                } else {
                    modelAndView.addObject("step", 1);
                    modelAndView.addObject("hasNotEmptySeats", true);
                }

            } catch (InvalidInputDataException e) {
                modelAndView.addObject("step", 1);
                modelAndView.addObject("invalidInputDataException", true);
            }
        } else {
            modelAndView.addObject("step", 1);
            if (!isValidTrainNumber(trainNumber)) {
                modelAndView.addObject("invalidTrainNumber", true);
            }
            if (!isValidStationName(dispatchStation)) {
                modelAndView.addObject("invalidDispatchStation", true);
            }
            if (!isValidDateStr(departureDate) || !isValidTimeStr(departureTime)) {
                modelAndView.addObject("invalidDepartureDate", true);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "step_2", method = RequestMethod.POST)
    public ModelAndView postStep2(@RequestParam(value = "firstName") String firstName,
                                  @RequestParam(value = "lastName") String lastName,
                                  @RequestParam(value = "birthdate") String birthdate,
                                  HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("buy_ticket");

        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("birthdate", birthdate);

        if (isValidPassengerNameOrSurname(firstName) && isValidPassengerNameOrSurname(lastName) &&
                isValidDateStr(birthdate)) {

            String trainNumber = (String) session.getAttribute("trainNumber");
            String dispatchStation = (String) session.getAttribute("dispatchStation");
            String departureDate = (String) session.getAttribute("departureDate");
            String departureTime = (String) session.getAttribute("departureTime");

            if (isValidTrainNumber(trainNumber) && isValidStationName(dispatchStation)
                    && isValidDateStr(departureDate) && isValidTimeStr(departureTime)) {

                Integer trainNumberInt = Integer.valueOf(trainNumber);
                Date date = DateHelper.convertDatetime(departureDate, departureTime);
                Date birth = DateHelper.convertDate(birthdate);

                try {
                    boolean registered = ticketService.isRegistered(trainNumberInt, dispatchStation, date, firstName, lastName, birth);

                    if (!registered) {

                        Float coast = ticketService.getTicketCost(trainNumberInt, dispatchStation, date);
                        if (coast != null) {
                            session.setAttribute("ticketCost", coast);
                        }

                        modelAndView.addObject("step", 3);
                    } else {
                        modelAndView.addObject("step", 2);
                        modelAndView.addObject("alreadyRegistered", true);
                    }

                } catch (InvalidInputDataException e) {
                    modelAndView.addObject("step", 2);
                    modelAndView.addObject("invalidInputDataException", true);
                }
            } else {
                modelAndView.addObject("step", 1);
                if (!isValidTrainNumber(trainNumber)) {
                    modelAndView.addObject("invalidTrainNumber", true);
                }
                if (!isValidStationName(dispatchStation)) {
                    modelAndView.addObject("invalidDispatchStation", true);
                }
                if (!isValidDateStr(departureDate) || !isValidTimeStr(departureTime)) {
                    modelAndView.addObject("invalidDepartureDate", true);
                }
            }

        } else {
            modelAndView.addObject("step", 2);
            if (!isValidPassengerNameOrSurname(firstName)) {
                modelAndView.addObject("invalidFirstName", true);
            }
            if (!isValidPassengerNameOrSurname(lastName)) {
                modelAndView.addObject("invalidLastName", true);
            }
            if (!isValidDateStr(birthdate)) {
                modelAndView.addObject("invalidBirthdate", true);
            }
        }
        return modelAndView;
    }

    @RequestMapping(value = "step_3", method = RequestMethod.POST)
    public ModelAndView postStep2(@RequestParam(value = "cardNumber") String cardNumber,
                                  @RequestParam(value = "cardHolder") String cardHolder,
                                  @RequestParam(value = "cardCVC") String cardCVC,
                                  @RequestParam(value = "cardExpiresDate") String cardExpiresDate,
                                  HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("buy_ticket");

        session.setAttribute("cardNumber", cardNumber);
        session.setAttribute("cardHolder", cardHolder);
        session.setAttribute("cardExpiresDate", cardExpiresDate);

        if (isValidCardNumber(cardNumber) && isValidCardHolder(cardHolder)
                && isValidCardCVC(cardCVC) && isValidShortDateStr(cardExpiresDate)) {

            String trainNumber = (String) session.getAttribute("trainNumber");
            String dispatchStation = (String) session.getAttribute("dispatchStation");
            String departureDate = (String) session.getAttribute("departureDate");
            String departureTime = (String) session.getAttribute("departureTime");

            Integer trainNumberInt = Integer.valueOf(trainNumber);
            Date date = DateHelper.convertDatetime(departureDate, departureTime);

            String firstName = (String) session.getAttribute("firstName");
            String lastName = (String) session.getAttribute("lastName");
            String birthdate = (String) session.getAttribute("birthdate");

            PassengerData passengerData = new PassengerData();
            passengerData.setName(firstName);
            passengerData.setSurname(lastName);
            passengerData.setBirthdate(DateHelper.convertDate(birthdate));

            try {

                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Integer userId = userService.getUserIdByUsername(userDetails.getUsername());
                TicketData ticketData = ticketService.buyTicket(userId, trainNumberInt, date, dispatchStation, passengerData);

                if (ticketData != null) {

                    modelAndView = new ModelAndView("buy_ticket_success");

                    session.setAttribute("ticketData", ticketData);

                    session.removeAttribute("trainNumber");
                    session.removeAttribute("dispatchStation");
                    session.removeAttribute("departureDate");
                    session.removeAttribute("departureTime");

                    session.removeAttribute("ticketCoast");

                    session.removeAttribute("firstName");
                    session.removeAttribute("lastName");
                    session.removeAttribute("birthdate");

                    session.removeAttribute("cardNumber");
                    session.removeAttribute("cardHolder");
                    session.removeAttribute("cardExpiresDate");
                }

            } catch (ClassCastException | NullPointerException e) {
                modelAndView = new ModelAndView("buy_ticket_error");
            } catch (HasNoEmptySeatsException e) {
                modelAndView.addObject("step", 1);
                modelAndView.addObject("hasNotEmptySeats", true);
            } catch (AlreadyRegisteredException e) {
                modelAndView.addObject("step", 2);
                modelAndView.addObject("alreadyRegistered", true);
            } catch (SalesStopException e) {
                modelAndView.addObject("step", 1);
                modelAndView.addObject("hasNotEnoughTime", true);
            } catch (InvalidInputDataException e) {
                modelAndView.addObject("step", 3);
                modelAndView.addObject("invalidInputDataException", true);
            }

        } else {
            modelAndView = new ModelAndView("buy_ticket");
            modelAndView.addObject("step", 3);
            if (!isValidCardNumber(cardNumber)) {
                modelAndView.addObject("invalidCardNumber", true);
            }
            if (!isValidCardHolder(cardHolder)) {
                modelAndView.addObject("invalidCardHolder", true);
            }
            if (!isValidCardCVC(cardCVC)) {
                modelAndView.addObject("invalidCardCVC", true);
            }
            if (!isValidCardCVC(cardExpiresDate)) {
                modelAndView.addObject("invalidCardExpiresDate", true);
            }
        }
        return modelAndView;

    }
}
