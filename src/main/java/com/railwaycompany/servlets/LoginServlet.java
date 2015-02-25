package com.railwaycompany.servlets;

import com.railwaycompany.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

public class LoginServlet extends HttpServlet {

    /**
     * Logger for UserHibernateDao class.
     */
    private static Logger log = Logger.getLogger(LoginServlet.class.getName());


    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getHeader("login");
        String password = req.getHeader("password");

        String resultPage = null;

        HttpSession session = req.getSession();

        log.info("Session isNew: " + session.isNew() + " Id: " + session.getId());

        try {
            resultPage = userService.signin(login, password);
        } catch (Exception e) {

        }

        if (resultPage == null) {
            resultPage = "/error.html";

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
            dispatcher.forward(req, resp);

        } else {
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(resultPage);
        }
    }
}
