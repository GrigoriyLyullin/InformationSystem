package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.exceptions.CouldNotPersistTokenException;
import com.railwaycompany.business.services.exceptions.IncorrectPasswordException;
import com.railwaycompany.business.services.exceptions.InvalidLoginOrPasswordException;
import com.railwaycompany.business.services.exceptions.UserDoesNotHaveAppropriateAccessRightsException;
import com.railwaycompany.business.services.interfaces.TokenService;
import com.railwaycompany.persistence.dao.interfaces.TokenDao;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.Token;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.presentation.authentication.RestParameters;
import com.railwaycompany.utils.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RestParameters parameters;

    @Override
    public boolean verifyToken(String key) {
        Token token = tokenDao.getToken(key);
        if (token != null) {
            if (!isExpired(token)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getToken(String login, String password)
            throws InvalidLoginOrPasswordException, UserDoesNotHaveAppropriateAccessRightsException,
            CouldNotPersistTokenException, IncorrectPasswordException {
        String tokenKey;
        User user = userDao.findUser(login);
        if (user != null) {
            if (user.getPassword().equals(HashHelper.hash(password))) {
                if (user.getUserRole().getRole().equals(parameters.getRole())) {
                    Token tokenByUser = tokenDao.findTokenByUserId(user.getId());
                    Date now = new Date();
                    if (tokenByUser == null) {
                        Token newToken = new Token();
                        newToken.setUser(user);
                        newToken.setCreationTime(now);
                        newToken.setTokenKey(generateTokenKey(user.getLogin(), now));
                        tokenDao.create(newToken);
                    } else if (isExpired(tokenByUser)) {
                        tokenByUser.setCreationTime(now);
                        tokenByUser.setTokenKey(generateTokenKey(user.getLogin(), now));
                        tokenDao.update(tokenByUser);
                    }
                    tokenByUser = tokenDao.findTokenByUserId(user.getId());
                    if (tokenByUser != null) {
                        tokenKey = tokenDao.findTokenByUserId(user.getId()).getTokenKey();
                    } else {
                        throw new CouldNotPersistTokenException();
                    }
                } else {
                    throw new UserDoesNotHaveAppropriateAccessRightsException();
                }
            } else {
                throw new IncorrectPasswordException();
            }
        } else {
            throw new InvalidLoginOrPasswordException();
        }
        return tokenKey;
    }

    private boolean isExpired(Token token) {
        long time = new Date().getTime() - token.getCreationTime().getTime();
        return time >= parameters.getExpirationTime();
    }

    private String generateTokenKey(String login, Date now) {
        return HashHelper.hash(HashHelper.hash(login) + HashHelper.hash(now.toString()));
    }
}
