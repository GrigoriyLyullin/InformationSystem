package com.railwaycompany.servlets;

import com.railwaycompany.services.AuthenticationService;
import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    private AuthenticationService authenticationService;

    @Override
    public void init() throws ServletException {
        authenticationService = ServiceFactorySingleton.getInstance().getAuthenticationService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        signOut(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        signOut(req, resp);
    }

    private void signOut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String authId = (String) session.getAttribute(AuthenticationService.AUTH_ID_ATTR);
            if (authId != null && !authId.isEmpty()) {
                String sessionId = session.getId();
                authenticationService.signOut(sessionId);
            }
            session.invalidate();
        }
        resp.sendRedirect("/");
    }
}
