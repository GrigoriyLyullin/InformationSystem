package com.railwaycompany.services;

import com.railwaycompany.dao.DaoFactory;
import com.railwaycompany.dao.UserDao;
import com.railwaycompany.entities.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The service implements users authentication on server.
 */
public class AuthenticationService {

    /**
     * Attribute name for authentication id.
     */
    public static final String AUTH_ID_ATTR = "authorization-id";

    /**
     * Hash algorithm name.
     */
    private static final String HASH_ALG = "MD5";

    /**
     * Logger for UserService class.
     */
    private static Logger log = Logger.getLogger(AuthenticationService.class.getName());

    /**
     * GenericDao<User> implementation for work with User entities.
     */
    private UserDao userDao;

    /**
     * Map contains pairs session id on authentication id.
     */
    private Map<String, String> sessionToAuthenticationId;

    /**
     * AuthenticationService constructor.
     *
     * @param daoFactory - For creating Dao objects for work with the database.
     */
    public AuthenticationService(DaoFactory daoFactory) {
        userDao = daoFactory.getUserDao();
        sessionToAuthenticationId = new HashMap<>();
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
     * Performs user's sign in from the system.
     *
     * @param sessionId - user's session id
     * @param login     - user's login
     * @param password  - user's password
     * @return
     */
    public String signIn(String sessionId, String login, String password) {

        String authenticationId = null;
        User user = userDao.findUser(login, password);
        if (user != null) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                authenticationId = generateAuthenticationId(login, password);
                sessionToAuthenticationId.put(sessionId, authenticationId);
            }
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
        }
    }

    /**
     * Generates authentication id.
     *
     * @param login    - user's login
     * @param password - user's password
     * @return Authentication id
     */
    private String generateAuthenticationId(String login, String password) {
        String id = generateHash(login + password);
        if (id == null) {
            log.log(Level.SEVERE, "Hash generation does not work");
            id = login;
        }
        return id;
    }

    /**
     * Generates hash string.
     *
     * @param inputStr - input string
     * @return Hash string
     */
    private String generateHash(String inputStr) {
        String generateHash = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance(HASH_ALG);
            md5.update(inputStr.getBytes());
            byte inputStrData[] = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < inputStrData.length; i++) {
                sb.append(Integer.toString((inputStrData[i] & 0xff) + 0x100, 16).substring(1));
            }
            generateHash = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            log.log(Level.SEVERE, "Algorithm \"" + HASH_ALG + "\" was not found.", e);
        }
        return generateHash;
    }
}