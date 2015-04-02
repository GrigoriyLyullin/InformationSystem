package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.services.exceptions.CouldNotPersistTokenException;
import com.railwaycompany.business.services.exceptions.IncorrectPasswordException;
import com.railwaycompany.business.services.exceptions.InvalidLoginOrPasswordException;
import com.railwaycompany.business.services.exceptions.UserDoesNotHaveAppropriateAccessRightsException;

public interface TokenService {

    boolean verifyToken(String token);

    String getToken(String login, String password) throws InvalidLoginOrPasswordException,
            UserDoesNotHaveAppropriateAccessRightsException, CouldNotPersistTokenException, IncorrectPasswordException;
}
