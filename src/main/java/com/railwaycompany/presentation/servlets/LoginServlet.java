package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.business.services.interfaces.ServiceFactory;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.utils.ValidationHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.railwaycompany.business.services.implementation.AuthenticationServiceImpl.*;

public class LoginServlet extends HttpServlet {

    /**
     * Logger for LoginServlet class.
     */
    private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

    /**
     * AuthenticationService uses for users authentication on server.
     */
    private AuthenticationService authenticationService;

    private UserService userService;

    @Override
    public void init() throws ServletException {
        ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
        authenticationService = serviceFactory.getAuthenticationService();
        userService = serviceFactory.getUserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String signInMessage = req.getParameter(SIGN_IN_MSG_ATTR);
        LOG.log(Level.FINE, SIGN_IN_MSG_ATTR + " : " + signInMessage);
        req.getSession().setAttribute(SIGN_IN_MSG_ATTR, signInMessage);
        resp.sendRedirect(ROOT_LOCATION);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);
        boolean validLogin = ValidationHelper.isValidLogin(login);
        boolean validPassword = ValidationHelper.isValidPassword(password);
        HttpSession session = req.getSession();
        boolean signIn = true;
        String signUpUrl = null;

        if (validLogin && validPassword) {
            LOG.log(Level.INFO, "User try to sign in with valid login: " + login + " and password: " + password);

            String sessionId = session.getId();
            String authenticationId = authenticationService.signIn(sessionId, login, password);

            if (authenticationId != null) {
                signIn = false;
                UserData userData = userService.getUserData(authenticationService.getUserId(sessionId, authenticationId));
                session.setAttribute(AUTH_ID_ATTR, authenticationId);
                session.setAttribute(USER_DATA_ATTR, userData);
                signUpUrl = (String) session.getAttribute(SIGN_IN_URL_ATTR);
                LOG.log(Level.INFO, "User successfully sign in");
            } else {
                LOG.log(Level.WARNING, "User cannot sign in");
            }
        } else {
            LOG.log(Level.INFO, "User try to sign in with invalid login: " + login + " and password: " + password);
        }
        session.setAttribute(SIGN_IN_ERROR_ATTR, signIn);
        session.setAttribute(SIGN_IN_ATTR, signIn);
        resp.sendRedirect((signUpUrl != null) ? signUpUrl : ROOT_LOCATION);
    }
}
