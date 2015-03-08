package com.railwaycompany.presentation.filters;

import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.AuthenticationService;

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

import static com.railwaycompany.business.services.implementation.AuthenticationServiceImpl.*;

public class AuthenticationFilter implements Filter {

    /**
     * Logger for AuthenticationFilter class.
     */
    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());

    /**
     * Private pages initial parameter in FilterConfig.
     */
    private static final String PAGES_INIT_PARAM = "Private pages";
    private static final String EMPLOYEE_PAGES_INIT_PARAM = "Employee pages";

    /**
     * Initial parameters splitter.
     */
    private static final String INIT_PARAM_SPLITTER = ";";

    /**
     * AuthenticationService using for users authentication on server.
     */
    private AuthenticationService authenticationService;

    /**
     * List contains private pages URI.
     */
    private List<String> privatePagesList;

    private List<String> employeePagesList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        privatePagesList = new ArrayList<>();
        employeePagesList = new ArrayList<>();
        authenticationService = ServiceFactorySingleton.getInstance().getAuthenticationService();
        String userInitParameter = filterConfig.getInitParameter(PAGES_INIT_PARAM);
        if (userInitParameter != null) {
            Collections.addAll(privatePagesList, userInitParameter.split(INIT_PARAM_SPLITTER));
        }
        String employeeInitParameter = filterConfig.getInitParameter(EMPLOYEE_PAGES_INIT_PARAM);
        if (employeeInitParameter != null) {
            Collections.addAll(employeePagesList, employeeInitParameter.split(INIT_PARAM_SPLITTER));
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        String requestURI = httpRequest.getRequestURI();

        if (privatePagesList.contains(requestURI) || employeePagesList.contains(requestURI)) {
            HttpServletResponse httpResponse = (HttpServletResponse) resp;
            HttpSession session = httpRequest.getSession();
            String sessionId = session.getId();

            String authId = (String) session.getAttribute(AUTH_ID_ATTR);
            if (authId != null && authenticationService.isAuthorized(sessionId, authId)) {
                if (employeePagesList.contains(requestURI)) {
                    if (authenticationService.isEmployee(sessionId, authId)) {
                        LOG.log(Level.INFO, "Employee with session id " + sessionId + ", authentication id: " +
                                authId + "is authorized. Request URI:" + requestURI);
                        chain.doFilter(req, resp);
                    } else {
                        httpResponse.sendRedirect(ROOT_LOCATION);
                    }
                } else {
                    LOG.log(Level.INFO, "User with session id " + sessionId + ", authentication id: " +
                            authId + "is authorized. Request URI:" + requestURI);
                    chain.doFilter(req, resp);
                }
            } else {
                LOG.log(Level.INFO, "User with session id: " + sessionId + " is not authorized. Request URI: "
                        + requestURI);

                session.setAttribute(SIGN_IN_URL_ATTR, requestURI);
                session.setAttribute(SIGN_IN_MSG_ATTR, getSignInMessage(requestURI));

                httpResponse.sendRedirect(ROOT_LOCATION);
            }
        } else {
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * Returns message depends on request URI.
     *
     * @param requestURI - request URI string
     * @return Message string or null, if specified message was not found
     */
    private String getSignInMessage(String requestURI) {
        String message = null;
        if (requestURI.contains("buy_ticket")) {
            message = "buy ticket";
        }
        return message;
    }
}
