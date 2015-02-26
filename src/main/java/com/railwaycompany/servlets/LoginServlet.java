package com.railwaycompany.servlets;

import com.railwaycompany.services.AuthenticationData;
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
     * Logger for UserHibernateDao class.
     */
    private static Logger log = Logger.getLogger(LoginServlet.class.getName());


    private AuthenticationService authenticationService;

    @Override
    public void init() throws ServletException {

        log.info("start init() ");

        try {
            ServiceFactory serviceFactory = ServiceFactorySingleton.getInstance();
            authenticationService = serviceFactory.getAuthenticationService();
        } catch (Exception e) {
            log.log(Level.WARNING, "", e);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        log.info("login: " + login + " password: " + password);

        AuthenticationData data = authenticationService.signIn(login, password);

        if (data == null) {
            resp.sendRedirect("/login_error.html");
        } else {

            HttpSession session = req.getSession();
            session.setAttribute("AuthenticationData", data);

            resp.getWriter().write(data.toString());
        }
    }
}
