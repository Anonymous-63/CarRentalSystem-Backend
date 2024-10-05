package com.anonymous63.crs.services;

import java.util.List;

public interface CrudService<T, ID> {
    T save(T entity);

    T update(T entity);

    T findById(ID id);

    List<T> findAll();
}
