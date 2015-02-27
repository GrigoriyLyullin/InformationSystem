package com.railwaycompany.servlets;

import com.railwaycompany.services.AuthenticationService;
import com.railwaycompany.services.ServiceFactory;
import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    /**
     * Logger for LoginServlet class.
     */
    private static Logger log = Logger.getLogger(LoginServlet.class.getName());

    private AuthenticationService authenticationService;

    @Override
    public void init() throws ServletException {
        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
        authenticationService = serviceFactory.getAuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String authId = (String) session.getAttribute(AuthenticationService.AUTH_ID_ATTR);
        if (authId != null) {
            session.setAttribute("signUp", false);
        } else {
            session.setAttribute("signUp", true);
        }
        getServletContext().getRequestDispatcher("/").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.log(Level.FINEST, "User sign in with login: \"" + login + "\" and password: \"" + password + "\"");

        HttpSession session = req.getSession();
        String sessionId = session.getId();
        String authId = authenticationService.signIn(sessionId, login, password);
        if (authId == null) {
            log.log(Level.INFO, "User try to sign in with login: \"" + login + "\" and password: \"" + password + "\"");
            session.setAttribute("signUpError", true);
        } else {
            session.setAttribute("signUpError", false);

            String userName = authenticationService.getUserName(sessionId, authId);
            String userSurname = authenticationService.getUserSurname(sessionId, authId);

            session.setAttribute(AuthenticationService.AUTH_ID_ATTR, authId);
            session.setAttribute(AuthenticationService.USER_NAME_ATTR, userName);
            session.setAttribute(AuthenticationService.USER_SURNAME_ATTR, userSurname);

        }
        getServletContext().getRequestDispatcher("/").forward(req, resp);
    }
}
