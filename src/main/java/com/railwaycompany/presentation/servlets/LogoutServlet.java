package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("logout")
public class LogoutServlet {

    /**
     * Attribute name for authentication id.
     */
    public static final String AUTH_ID_ATTR = "authenticationId";
    private static final String INDEX_PAGE = "/WEB-INF/index.jsp";
    /**
     * AuthenticationService uses for users authentication on server.
     */
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.GET)
    protected String doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return signOut(req, resp);
    }

    @RequestMapping(method = RequestMethod.POST)
    protected String doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return signOut(req, resp);
    }

    /**
     * Sign out user with specified authentication and session id.
     *
     * @param req  - http servlet request
     * @param resp - http servlet response
     * @throws IOException - If an input or output exception occurs
     */
    private String signOut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            String authId = (String) session.getAttribute(AUTH_ID_ATTR);
            if (authId != null && !authId.isEmpty()) {
                String sessionId = session.getId();
                authenticationService.signOut(sessionId);
            }
            session.invalidate();
        }
//        getServletContext().getRequestDispatcher(INDEX_PAGE).forward(req, resp);
        return "index";
    }
}
