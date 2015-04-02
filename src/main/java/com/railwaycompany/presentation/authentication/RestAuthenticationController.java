package com.railwaycompany.presentation.authentication;

import com.railwaycompany.business.services.exceptions.CouldNotPersistTokenException;
import com.railwaycompany.business.services.exceptions.IncorrectPasswordException;
import com.railwaycompany.business.services.exceptions.InvalidLoginOrPasswordException;
import com.railwaycompany.business.services.exceptions.UserDoesNotHaveAppropriateAccessRightsException;
import com.railwaycompany.business.services.interfaces.TokenService;
import com.railwaycompany.utils.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("authenticate")
public class RestAuthenticationController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RestParameters parameters;

    @RequestMapping(method = RequestMethod.POST)
    public void authenticate(@RequestParam(value = "login") String login,
                             @RequestParam(value = "password") String password,
                             HttpServletResponse response) {
        if (ValidationHelper.isValidLogin(login) && ValidationHelper.isValidPassword(password)) {
            try {
                String token = tokenService.getToken(login, password);
                if (token != null) {
                    response.setHeader(parameters.getTokenHeader(), token);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } catch (InvalidLoginOrPasswordException | IncorrectPasswordException |
                    UserDoesNotHaveAppropriateAccessRightsException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } catch (CouldNotPersistTokenException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public void getStub(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteStub(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void putStub(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
    }
}
