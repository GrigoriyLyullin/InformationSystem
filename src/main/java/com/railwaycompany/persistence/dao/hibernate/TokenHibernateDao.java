package com.railwaycompany.persistence.dao.hibernate;


import com.railwaycompany.persistence.dao.interfaces.TokenDao;
import com.railwaycompany.persistence.entities.Token;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;

@Repository
@Transactional
public class TokenHibernateDao extends HibernateDao<Token> implements TokenDao {

    private static final String GET_TOKEN_BY_KEY = "SELECT t FROM Token t WHERE t.tokenKey = :tokenKey";

    private static final String GET_TOKEN_BY_LOGIN = "SELECT t FROM Token t WHERE t.user.id = :id";

    @Override
    public Token getToken(String tokenKey) {
        Query query = entityManager.createQuery(GET_TOKEN_BY_KEY);
        query.setParameter("tokenKey", tokenKey);

        Token token = null;
        try {
            Object result = query.getSingleResult();
            if (result instanceof Token) {
                token = (Token) result;
            }
        } catch (Exception e) {
        }
        return token;
    }

    @Override
    public Token findTokenByUserId(int id) {
        Query query = entityManager.createQuery(GET_TOKEN_BY_LOGIN);
        query.setParameter("id", id);

        Token token = null;
        try {
            Object result = query.getSingleResult();
            if (result instanceof Token) {
                token = (Token) result;
            }
        } catch (Exception e) {
        }
        return token;
    }
}
