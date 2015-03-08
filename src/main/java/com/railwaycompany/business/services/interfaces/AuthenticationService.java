package com.railwaycompany.business.services.interfaces;

import java.io.IOException;

/**
 * Service interface for users authentication on server.
 */
public interface AuthenticationService {

    /**
     * Checks that user has been authorized.
     *
     * @param sessionId        - user's session id
     * @param authenticationId - user's authentication id
     * @return True if user with this credentials has been authorized, otherwise - False.
     */
    boolean isAuthorized(String sessionId, String authenticationId);

    /**
     * Performs user's sign in from the system.
     *
     * @param sessionId - user's session id
     * @param login     - user's login
     * @param password  - user's password
     * @return Authentication id or null if user with this login and password was not found
     */
    String signIn(String sessionId, String login, String password);

    /**
     * Returns user id for the session and authentication id.
     *
     * @param sessionId        - user's session id
     * @param authenticationId - user's authentication id
     * @return User id value or null if id was not found for this credentials.
     */
    Integer getUserId(String sessionId, String authenticationId);

    /**
     * Performs user's sign out from the system.
     *
     * @param sessionId - user's session id
     */
    void signOut(String sessionId) throws IOException;

    /**
     * Checks that this user is employee.
     *
     * @param sessionId        - user's session id
     * @param authenticationId - user's authentication id
     * @return True if this user is employee, False - otherwise
     */
    boolean isEmployee(String sessionId, String authenticationId);
}
