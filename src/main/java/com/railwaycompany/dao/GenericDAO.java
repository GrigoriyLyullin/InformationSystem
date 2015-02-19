package com.railwaycompany.dao;

import java.io.Serializable;

public interface GenericDAO<T extends Serializable> {

    void create(T t);

    T read(int key);

    void update(T t);

    void delete(T t);
}