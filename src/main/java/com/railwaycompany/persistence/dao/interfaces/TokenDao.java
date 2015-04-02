package com.railwaycompany.persistence.dao.interfaces;

import com.railwaycompany.persistence.entities.Token;

public interface TokenDao extends GenericDAO<Token> {

    Token getToken(String tokenKey);

    Token findTokenByUserId(int id);
}
