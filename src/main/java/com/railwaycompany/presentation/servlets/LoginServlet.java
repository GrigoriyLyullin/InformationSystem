package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
//@RequestMapping("login")
public class LoginServlet {

    /**
     * Parameter name for login.
     */
    public static final String LOGIN_PARAM = "login";
    /**
     * Parameter name for password.
     */
    public static final String PASSWORD_PARAM = "password";
    /**
     * Attribute name for authentication id.
     */
    public static final String AUTH_ID_ATTR = "authenticationId";
    /**
     * Attribute name for user data.
     */
    public static final String USER_DATA_ATTR = "userData";
    /**
     * Attribute name for sign in error.
     */
    public static final String SIGN_IN_ERROR_ATTR = "signInError";
    /**
     * Attribute name for sign in.
     */
    public static final String SIGN_IN_ATTR = "signIn";
    /**
     * Attribute name for sign in message.
     */
    public static final String SIGN_IN_MSG_ATTR = "signInMessage";
    private static final String INDEX_PAGE = "/WEB-INF/index.jsp";
    /**
     * Logger for LoginServlet class.
     */
    private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

    /**
     * AuthenticationService uses for users authentication on server.
     */
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;


//    @RequestMapping(method = RequestMethod.GET)
//    protected String doGet(HttpServletRequest req) {
//        String signInMessage = req.getParameter(SIGN_IN_MSG_ATTR);
//        req.getSession().setAttribute(SIGN_IN_MSG_ATTR, signInMessage);
//        return "login";
//    }
//
//    @RequestMapping(method = RequestMethod.POST)
//    protected String doPost(HttpServletRequest req, HttpServletResponse resp) {
//        String login = req.getParameter(LOGIN_PARAM);
//        String password = req.getParameter(PASSWORD_PARAM);
//        boolean validLogin = ValidationHelper.isValidLogin(login);
//        boolean validPassword = ValidationHelper.isValidPassword(password);
//        HttpSession session = req.getSession();
//        boolean signIn = true;
//
//        if (validLogin && validPassword) {
//            LOG.log(Level.INFO, "User try to sign in with valid login: " + login + " and password: " + password);
//
//            String sessionId = session.getId();
//            String authenticationId = authenticationService.signIn(sessionId, login, password);
//
//            if (authenticationId != null) {
//                signIn = false;
//                UserData userData = userService.getUserData(authenticationService.getUserId(sessionId, authenticationId));
//                session.setAttribute(AUTH_ID_ATTR, authenticationId);
//                session.setAttribute(USER_DATA_ATTR, userData);
//                LOG.log(Level.INFO, "User successfully sign in");
//            } else {
//                LOG.log(Level.WARNING, "User cannot sign in");
//            }
//        } else {
//            LOG.log(Level.WARNING, "User try to sign in with invalid login: " + login + " and password: " + password);
//        }
//        session.setAttribute(SIGN_IN_ERROR_ATTR, signIn);
//        session.setAttribute(SIGN_IN_ATTR, signIn);
//        return "index";
//    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
        } else {
            request.getSession().setAttribute("authenticationId", "authenticationId");
        }

        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");

        return model;

    }

    // customize the error message
    private String getErrorMessage(HttpServletRequest request, String key) {

        Exception exception = (Exception) request.getSession().getAttribute(key);

        String error;
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }

        return error;
    }
}
