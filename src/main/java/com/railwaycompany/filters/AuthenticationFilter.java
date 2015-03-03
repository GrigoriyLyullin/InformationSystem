package com.railwaycompany.filters;

import com.railwaycompany.services.AuthenticationService;
import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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
        for (String page : privatePages) {
            privatePagesList.add(page);
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) req;

        log.info("httpRequest.getRequestURI(): " + httpRequest.getRequestURI());

        if (privatePagesList.contains(httpRequest.getRequestURI())) {

            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            HttpSession session = httpRequest.getSession();
            String sessionId = session.getId();

            String authId = (String) session.getAttribute(AuthenticationService.AUTH_ID_ATTR);
            if (authId != null) {
                if (authenticationService.isAuthorized(sessionId, authId)) {
                    log.log(Level.INFO, "User with session id " + sessionId + " is authorized. Authentication id " +
                            authId);
                    chain.doFilter(req, resp);
                }
            } else {
                log.log(Level.INFO, "User with session id " + sessionId + " is not authorized.");
                session.setAttribute("signUpUrl", httpRequest.getRequestURI());
                httpResponse.sendRedirect("/login");
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }
}
