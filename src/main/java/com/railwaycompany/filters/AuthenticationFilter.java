package com.railwaycompany.filters;

import com.railwaycompany.services.AuthenticationService;
import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthenticationFilter implements Filter {

    /**
     * Logger for AuthenticationFilter class.
     */
    private Logger log = Logger.getLogger(AuthenticationFilter.class.getName());

    private AuthenticationService authenticationService;

    private List<String> privatePagesList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authenticationService = ServiceFactorySingleton.getInstance().getAuthenticationService();
        String privatePagesStr = filterConfig.getInitParameter("Private pages");
        String[] privatePages = privatePagesStr.split(";");

        privatePagesList = new ArrayList<>();
        Collections.addAll(privatePagesList, privatePages);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        String requestURI = httpRequest.getRequestURI();
        log.info("httpRequest.getRequestURI(): " + requestURI);

        if (privatePagesList.contains(requestURI)) {

            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            HttpSession session = httpRequest.getSession();
            String sessionId = session.getId();

            String authId = (String) session.getAttribute(AuthenticationService.AUTH_ID_ATTR);
            if (authId != null) {
                if (authenticationService.isAuthorized(sessionId, authId)) {

                    log.log(Level.INFO, "User with session id " + sessionId + " is authorized. Authentication id " +
                            authId + " requestURI:" + requestURI);


                    chain.doFilter(req, resp);
                }
            } else {

                log.log(Level.INFO, "User with session id " + sessionId + " is not authorized requestURI: " + requestURI);

                session.setAttribute("signInUrl", requestURI);

                switch (requestURI) {
                    case "/buy_ticket":
                        session.setAttribute("signInMessage", "buy ticket");
                        break;
                    case "/buy_ticket.jsp":
                        session.setAttribute("signInMessage", "buy ticket");
                        break;
                }

                httpResponse.sendRedirect("/");
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
