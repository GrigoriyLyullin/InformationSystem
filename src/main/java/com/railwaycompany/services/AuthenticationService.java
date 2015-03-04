package com.railwaycompany.services;

import com.railwaycompany.dao.abstractDao.DaoFactory;
import com.railwaycompany.dao.abstractDao.UserDao;
import com.railwaycompany.entities.User;
import com.railwaycompany.serviceBeans.UserData;
import com.railwaycompany.utils.HashHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The service implements users authentication on server.
 */
public class AuthenticationService {

    /**
     * Logger for LoginServlet class.
     */
    private static final Logger LOG = Logger.getLogger(AuthenticationService.class.getName());

    public static final String ROOT_LOCATION = "/";

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
     * Attribute name for sign in url.
     */
    public static final String SIGN_IN_URL_ATTR = "signInUrl";

    /**
     * Attribute name for sign in message.
     */
    public static final String SIGN_IN_MSG_ATTR = "signInMessage";

    /**
     * GenericDAO<User> implementation for work with User entities.
     */
    private final UserDao userDao;

    /**
     * Map contains pairs session id on authentication id.
     */
    private final Map<String, String> sessionToAuthenticationId;

    /**
     * Map contains pairs session id on user id.
     */
    private final Map<String, Integer> sessionIdToUserId;

    /**
     * AuthenticationService constructor.
     *
     * @param daoFactory - For creating Dao objects for work with the database.
     */
    public AuthenticationService(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
        sessionToAuthenticationId = new HashMap<>();
        sessionIdToUserId = new HashMap<>();
    }

    /**
     * Checks that user has been authorized.
     *
     * @param sessionId        - user's session id
     * @param authenticationId - user's authentication id
     * @return True if user with this credentials has been authorized, otherwise - False.
     */
    public boolean isAuthorized(String sessionId, String authenticationId) {
        if (sessionToAuthenticationId.containsKey(sessionId)) {
            if (sessionToAuthenticationId.get(sessionId).equals(authenticationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns user data object.
     *
     * @param sessionId        - user's session id
     * @param authenticationId - user's authentication id
     * @return user data object.
     */
    public UserData getUserData(String sessionId, String authenticationId) {
        UserData userData = null;
        if (isAuthorized(sessionId, authenticationId)) {
            Integer userId = sessionIdToUserId.get(sessionId);
            User user = userDao.read(userId);
            if (user != null) {
                userData = new UserData();
                userData.setId(user.getId());
                userData.setName(user.getName());
                userData.setSurname(user.getSurname());
            }
        }
        return userData;
    }

    /**
     * Performs user's sign in from the system.
     *
     * @param sessionId - user's session id
     * @param login     - user's login
     * @param password  - user's password
     * @return Authentication id or null if user with this login and password was not found
     */
    public String signIn(String sessionId, String login, String password) {
        String authenticationId = null;
        User user = userDao.findUser(login, password);
        if (user != null) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                authenticationId = HashHelper.generateRandomHash();
                sessionToAuthenticationId.put(sessionId, authenticationId);
                sessionIdToUserId.put(sessionId, user.getId());
            }
        } else {
            LOG.log(Level.INFO, "User with login:\"" + login + "\" and password: \"" + password + "\" was not found");
        }
        return authenticationId;
    }

    /**
     * Performs user's sign out from the system.
     *
     * @param sessionId - user's session id
     */
    public void signOut(String sessionId) {
        if (sessionToAuthenticationId.containsKey(sessionId)) {
            sessionToAuthenticationId.remove(sessionId);
            sessionIdToUserId.remove(sessionId);
        }
    }
}