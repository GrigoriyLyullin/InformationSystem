package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The service implements users authentication on server.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Logger for LoginServlet class.
     */
    private static final Logger LOG = Logger.getLogger(AuthenticationServiceImpl.class.getName());
    /**
     * GenericDAO<User> implementation for work with User entities.
     */
    @Autowired
    private UserDao userDao;
    /**
     * Map contains pairs session id on authentication id.
     */
    private final Map<String, String> sessionToAuthenticationId;
    /**
     * Map contains pairs session id on user id.
     */
    private final Map<String, Integer> sessionIdToUserId;
    /**
     * Map contains pairs session id on user employee flag.
     */
    private final Map<String, Boolean> sessionIdToIsEmployee;
    /**
     * AuthenticationServiceImpl constructor.
     */
    public AuthenticationServiceImpl() {
        sessionToAuthenticationId = new ConcurrentHashMap<>();
        sessionIdToUserId = new ConcurrentHashMap<>();
        sessionIdToIsEmployee = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAuthorized(String sessionId, String authenticationId) {
        if (sessionToAuthenticationId.containsKey(sessionId)) {
            if (sessionToAuthenticationId.get(sessionId).equals(authenticationId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String signIn(String sessionId, String login, String password) {
        String authenticationId = null;
        String passwordHash = HashHelper.hash(password);
        User user = userDao.findUser(login, passwordHash);
        if (user != null) {
            if (user.getLogin().equals(login) && user.getPassword().equals(passwordHash)) {
                authenticationId = HashHelper.generateRandomHash();
                sessionToAuthenticationId.put(sessionId, authenticationId);
                sessionIdToUserId.put(sessionId, user.getId());
                sessionIdToIsEmployee.put(sessionId, user.isEmployee());
            }
        } else {
            LOG.log(Level.INFO, "User with login:\"" + login + "\" and password: \"" + password + "\" was not found");
        }
        return authenticationId;
    }

    @Override
    public Integer getUserId(String sessionId, String authenticationId) {
        Integer userId = null;
        if (isAuthorized(sessionId, authenticationId)) {
            if (sessionIdToUserId.containsKey(sessionId)) {
                userId = sessionIdToUserId.get(sessionId);
            }
        }
        return userId;
    }

    @Override
    public void signOut(String sessionId) {
        if (sessionToAuthenticationId.containsKey(sessionId)) {
            sessionToAuthenticationId.remove(sessionId);
            sessionIdToUserId.remove(sessionId);
        }
    }

    @Override
    public boolean isEmployee(String sessionId, String authenticationId) {
        boolean isEmployee = false;
        if (isAuthorized(sessionId, authenticationId)) {
            if (sessionIdToIsEmployee.containsKey(sessionId)) {
                isEmployee = sessionIdToIsEmployee.get(sessionId);
            }
        }
        return isEmployee;
    }
}