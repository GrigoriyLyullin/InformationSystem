package com.railwaycompany.persistence.dao.jdbc;

import com.railwaycompany.persistence.dao.interfaces.GenericDAO;
import com.railwaycompany.persistence.dao.interfaces.TokenDao;
import com.railwaycompany.persistence.entities.Token;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTokenDao implements GenericDAO<Token>, TokenDao {

    @Override
    public Token getToken(String tokenKey) {
        return null;
    }

    @Override
    public Token findTokenByUserId(int id) {
        return null;
    }

    @Override
    public void create(Token entity) {

    }

    @Override
    public Token read(int key) {
        return null;
    }

    @Override
    public Token update(Token entity) {
        return null;
    }

    @Override
    public void delete(Token entity) {

    }
}
