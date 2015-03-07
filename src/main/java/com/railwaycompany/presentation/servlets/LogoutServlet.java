package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.business.services.implementation.AuthenticationServiceImpl;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {

    /**
     * AuthenticationService uses for users authentication on server.
     */
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

    /**
     * Sign out user with specified authentication and session id.
     *
     * @param req  - http servlet request
     * @param resp - http servlet response
     * @throws IOException - If an input or output exception occurs
     */
    private void signOut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String authId = (String) session.getAttribute(AuthenticationServiceImpl.AUTH_ID_ATTR);
            if (authId != null && !authId.isEmpty()) {
                String sessionId = session.getId();
                authenticationService.signOut(sessionId);
            }
            session.invalidate();
        }
        resp.sendRedirect(AuthenticationServiceImpl.ROOT_LOCATION);
    }
}
