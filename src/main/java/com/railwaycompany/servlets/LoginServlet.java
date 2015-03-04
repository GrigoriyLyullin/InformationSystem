package com.railwaycompany.servlets;

import com.railwaycompany.serviceBeans.UserData;
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
        req.getSession().setAttribute("signInMessage", req.getParameter("signInMessage"));
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.log(Level.INFO, "User sign in with login: \"" + login + "\" and password: \"" + password + "\"");

        HttpSession session = req.getSession();

        session.setAttribute("signInMessage", null);

        String sessionId = session.getId();
        String authenticationId = authenticationService.signIn(sessionId, login, password);
        if (authenticationId == null) {
            log.log(Level.INFO, "User try to sign in with login: \"" + login + "\" and password: \"" + password + "\"");

            session.setAttribute("signInError", true);
            session.setAttribute("signIn", true);


            resp.sendRedirect("/");

        } else {

            session.setAttribute("signInError", false);
            session.setAttribute("signIn", false);

            UserData userData = authenticationService.getUserData(sessionId, authenticationId);

            session.setAttribute(AuthenticationService.AUTH_ID_ATTR, authenticationId);
            session.setAttribute(AuthenticationService.USER_DATA_ATTR, userData);

            String signUpUrl = (String) session.getAttribute("signInUrl");
            if (signUpUrl != null) {
                if (!signUpUrl.equals("/")) {
                    resp.sendRedirect(signUpUrl);
                }
            } else {
                resp.sendRedirect("/");
            }
        }
    }
}
