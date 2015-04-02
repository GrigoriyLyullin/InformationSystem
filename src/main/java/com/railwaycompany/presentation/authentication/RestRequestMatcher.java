package com.railwaycompany.presentation.authentication;

import com.railwaycompany.business.services.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class RestRequestMatcher implements RequestMatcher {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RestParameters parameters;

    @Override
    public boolean matches(HttpServletRequest request) {
        String token = request.getHeader(parameters.getTokenHeader());
        return token == null || !tokenService.verifyToken(token);
    }
}
