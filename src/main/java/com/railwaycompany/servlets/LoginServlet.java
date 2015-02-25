package com.railwaycompany.servlets;

import com.railwaycompany.services.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        String resultPage = "/error.html";

        try {
            resultPage = userService.signin(login, password);
        } catch (Exception e) {

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(resultPage);
        dispatcher.forward(req, resp);
    }
}
